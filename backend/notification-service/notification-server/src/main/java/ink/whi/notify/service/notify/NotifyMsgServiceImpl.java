package ink.whi.notify.service.notify;

import ink.whi.comment.client.CommentClient;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.NotifyStatEnum;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.model.dto.*;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.notify.repo.dao.NotifyMsgDao;
import ink.whi.notify.service.NotifyMsgService;
import ink.whi.video.client.VideoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
@Service
public class NotifyMsgServiceImpl implements NotifyMsgService {

    @Autowired
    private NotifyMsgDao notifyMsgDao;

    @Resource
    private VideoClient videoClient;

    @Resource
    private CommentClient commentClient;

    /**
     * 查询用户未读消息数
     * @param userId
     * @return
     */
    @Override
    public int queryUserNotifyMsgCount(Long userId) {
        return notifyMsgDao.countByUserIdAndStat(userId, NotifyStatEnum.UNREAD);
    }

    /**
     * 查询用户各类未读消息数
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> queryUnreadCounts(Long userId) {
        if (!NumUtil.upZero(ReqInfoContext.getReqInfo().getMsgNum())) {
            return Collections.emptyMap();
        }
        Map<Integer, Integer> map = notifyMsgDao.groupCountByUserIdAndStat(userId, NotifyStatEnum.UNREAD.getStat());
        // 指定先后顺序
        Map<String, Integer> res = new LinkedHashMap<>();
        initCnt(NotifyTypeEnum.COMMENT, map, res);
        initCnt(NotifyTypeEnum.REPLY, map, res);
        initCnt(NotifyTypeEnum.PRAISE, map, res);
        initCnt(NotifyTypeEnum.COLLECT, map, res);
        initCnt(NotifyTypeEnum.FOLLOW, map, res);
        initCnt(NotifyTypeEnum.SYSTEM, map, res);
        return res;
    }

    private void initCnt(NotifyTypeEnum type, Map<Integer, Integer> map, Map<String, Integer> result) {
        result.put(type.name().toLowerCase(), map.getOrDefault(type.getType(), 0));
    }

    /**
     * 根据消息类型查询用户消息
     * @param userId
     * @param typeEnum
     * @param page
     * @return
     */
    @Override
    public PageListVo<NotifyMsgDTO> queryUserNotices(Long userId, NotifyTypeEnum typeEnum, PageParam page) {
        List<NotifyMsgDTO> list = notifyMsgDao.listNotifyMsgByUserIdAndType(userId, typeEnum, page);
        // 将消息设为已读
        notifyMsgDao.updateNotifyMsgToRead(list);
        // 更新全局总的消息数
        ReqInfoContext.getReqInfo().setMsgNum(queryUserNotifyMsgCount(userId));
        // todo：更新用户关系
        return PageListVo.newVo(list, page.getPageSize());
    }

    /**
     * 用户评论消息
     * @param comment
     */
    @Override
    public void saveCommentNotify(CommentDTO comment) {
        SimpleVideoInfoDTO video = videoClient.queryBasicVideoInfo(comment.getVideoId());
        notifyMsgDao.saveCommentNotify(comment, video.getUserId());
    }

    /**
     * 用户回复消息
     * @param comment
     */
    @Override
    public void saveReplyNotify(CommentDTO comment) {
        CommentDTO parentComment = commentClient.queryComment(comment.getParentCommentId());
        notifyMsgDao.saveReplyNotify(comment, parentComment.getVideoId());
    }

    /**
     * 视频点赞消息
     * @param foot
     */
    @Override
    public void saveVideoPraise(UserFootDTO foot){
        notifyMsgDao.saveVideoPraise(foot);
    }

    /**
     * 视频收藏消息
     * @param foot
     */
    @Override
    public void saveVideoCollect(UserFootDTO foot){
        notifyMsgDao.saveVideoCollect(foot);
    }

    /**
     * 视频取消点赞消息
     * @param foot
     */
    @Override
    public void removeVideoPraise(UserFootDTO foot) {
        notifyMsgDao.removeVideoPraise(foot);
    }

    /**
     * 视频取消收藏消息
     * @param foot
     */
    @Override
    public void removeVideoCollect(UserFootDTO foot) {
        notifyMsgDao.removeVideoCollect(foot);
    }

    /**
     * 用户关注消息
     * @param relation
     */
    @Override
    public void saveFollowNotify(UserRelationDTO relation) {
        notifyMsgDao.saveFollowNotify(relation);
    }

    /**
     * 用户取消关注消息
     * @param relation
     */
    @Override
    public void removeFollowNotify(UserRelationDTO relation) {
        notifyMsgDao.removeFollowNotify(relation);
    }

    /**
     * 用户注册系统消息
     * @param userId
     */
    @Override
    public void saveRegisterSystemNotify(Long userId) {
        notifyMsgDao.saveRegisterSystemNotify(userId);
    }
}