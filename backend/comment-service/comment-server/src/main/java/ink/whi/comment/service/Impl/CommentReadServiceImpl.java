package ink.whi.comment.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import ink.whi.comment.dto.BaseCommentDTO;
import ink.whi.comment.dto.SubCommentDTO;
import ink.whi.comment.dto.TopCommentDTO;
import ink.whi.comment.repo.converter.CommentConverter;
import ink.whi.comment.repo.dao.CommentDao;
import ink.whi.comment.repo.entity.CommentDO;
import ink.whi.comment.service.CommentReadService;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.PraiseStatEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Service
public class CommentReadServiceImpl implements CommentReadService {

    @Autowired
    private CommentDao commentDao;

    @Resource
    private UserClient userClient;

    @Override
    public Integer queryCommentCount(Long videoId) {
        return commentDao.countCommentByVideoId(videoId);
    }

    @Override
    public CommentDTO queryComment(Long commentId) {
        CommentDO comment = commentDao.getById(commentId);
        return CommentConverter.toDto(comment);
    }

    @Override
    public PageListVo<TopCommentDTO> queryVideoComments(Long videoId, PageParam page) {
        // 1.查询一级评论
        List<CommentDO> comments = commentDao.listTopCommentList(videoId, page);
        if (CollectionUtils.isEmpty(comments)) {
            return PageListVo.emptyVo();
        }
        // map 存 commentId -> 评论
        Map<Long, TopCommentDTO> topComments = comments.stream().collect(Collectors.toMap(CommentDO::getId, CommentConverter::toTopDto));

        // 2.查询非一级评论
        List<CommentDO> subComments = commentDao.listSubCommentIdMappers(videoId, topComments.keySet());

        // 3.构建一级评论的子评论
        buildCommentRelation(subComments, topComments);

        // 4.挑出需要返回的数据，排序，并补齐对应的用户信息，最后排序返回
        List<TopCommentDTO> result = new ArrayList<>();
        comments.forEach(comment -> {
            TopCommentDTO dto = topComments.get(comment.getId());
            fillTopCommentInfo(dto);
            result.add(dto);
        });

        // 返回结果根据时间进行排序
        Collections.sort(result);
        return PageListVo.newVo(result, page.getPageSize());
    }

    /**
     * 构建父子评论关系
     */
    private void buildCommentRelation(List<CommentDO> subComments, Map<Long, TopCommentDTO> topComments) {
        Map<Long, SubCommentDTO> subCommentMap = subComments.stream().collect(Collectors.toMap(CommentDO::getId, CommentConverter::toSubDto));
        subComments.forEach(comment -> {
            TopCommentDTO top = topComments.get(comment.getTopCommentId());
            if (top == null) {
                return;
            }
            SubCommentDTO sub = subCommentMap.get(comment.getId());
            top.getChildComments().add(sub);
            if (Objects.equals(comment.getTopCommentId(), comment.getParentCommentId())) {
                return;
            }

            SubCommentDTO parent = subCommentMap.get(comment.getParentCommentId());
            sub.setParentContent(parent == null ? "~~该评论已删除~~" : parent.getCommentContent());
        });
    }

    /**
     * 填充评论对应的信息
     *
     * @param comment
     */
    private void fillTopCommentInfo(TopCommentDTO comment) {
        fillCommentInfo(comment);
        comment.getChildComments().forEach(this::fillCommentInfo);
        Collections.sort(comment.getChildComments());
    }

    /**
     * 填充评论对应的信息，如用户信息，点赞数等
     *
     * @param comment
     */
    private void fillCommentInfo(BaseCommentDTO comment) {
        SimpleUserInfoDTO userInfo = userClient.queryBasicUserInfo(comment.getUserId());
        if (userInfo == null) {
            // 如果用户注销，给一个默认的用户
            comment.setUserName("账号已注销");
            comment.setUserPhoto("");
            if (comment instanceof TopCommentDTO) {
                ((TopCommentDTO) comment).setCommentCount(0);
            }
        } else {
            comment.setUserName(userInfo.getUserName());
            comment.setUserPhoto(userInfo.getPicture());
            if (comment instanceof TopCommentDTO) {
                ((TopCommentDTO) comment).setCommentCount(((TopCommentDTO) comment).getChildComments().size());
            }
        }

        // 点赞数
        Integer praiseCount = userClient.queryCommentPraiseCount(comment.getCommentId());
        comment.setPraiseCount(praiseCount);

        // 查询当前登录用于是否点赞过
        Long loginUserId = ReqInfoContext.getReqInfo().getUserId();
        if (loginUserId != null) {
            // 判断当前用户是否点过赞
            UserFootDTO foot = userClient.queryUserFoot(comment.getCommentId(), VideoTypeEnum.COMMENT.getCode(), loginUserId);
            comment.setPraised(foot != null && Objects.equals(foot.getPraiseStat(), PraiseStatEnum.PRAISE.getCode()));
        } else {
            comment.setPraised(false);
        }
    }
}
