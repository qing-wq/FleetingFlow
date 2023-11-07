package ink.whi.comment.service.Impl;

import ink.whi.comment.model.req.CommentSaveReq;
import ink.whi.comment.repo.converter.CommentConverter;
import ink.whi.comment.repo.dao.CommentDao;
import ink.whi.comment.repo.entity.CommentDO;
import ink.whi.comment.service.CommentWriteService;
import ink.whi.common.enums.PushStatusEnum;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.notify.constants.VideoMqConstants;
import ink.whi.user.client.UserClient;
import ink.whi.video.client.VideoClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: qing
 * @Date: 2023/5/2
 */
@Service
public class CommentWriteServiceImpl implements CommentWriteService {

    @Autowired
    private CommentDao commentDao;

    @Resource
    private UserClient userClient;

    @Resource
    private VideoClient videoClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveComment(CommentSaveReq req) {
        CommentDO comment = null;
        if (req.getCommentId() == null) {
            comment = addComment(req);
        } else {
            comment = updateComment(req);
        }
        return comment.getId();
    }

    private CommentDO addComment(CommentSaveReq req) {
        SimpleVideoInfoDTO video = videoClient.queryBasicVideoInfo(req.getVideoId());
        if (video.getStatus() == PushStatusEnum.REVIEW.getCode()) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_OPERATE, "审核中的视频不能评论");
        }

        CommentDO comment = CommentConverter.toDo(req);
        Long parentCommentUser = getParentComment(comment);
        commentDao.save(comment);

        // 保存评论足迹
        userClient.saveCommentFoot(CommentConverter.toDto(comment), video.getAuthorId(), parentCommentUser);

        // 发布事件
        rabbitTemplate.convertAndSend(VideoMqConstants.VIDEO_TOPIC_EXCHANGE, VideoMqConstants.VIDEO_COMMENT_KEY, comment);
        if (NumUtil.upZero(parentCommentUser)){
            rabbitTemplate.convertAndSend(VideoMqConstants.VIDEO_TOPIC_EXCHANGE, VideoMqConstants.VIDEO_REPLY_KEY, comment);
        }
        return comment;
    }

    private CommentDO updateComment(CommentSaveReq req) {
        CommentDO record = commentDao.getById(req.getCommentId());
        if (record == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "评论不存在：" + req.getCommentId());
        }
        record.setContent(req.getCommentContent());
        record.setUpdateTime(new Date());
        commentDao.updateById(record);
        return record;
    }

    /**
     * 获取父评论用户
     * @param comment
     * @return
     */
    private Long getParentComment(CommentDO comment) {
        if (!NumUtil.upZero(comment.getParentCommentId())) {
            return null;
        }
        CommentDO parent = commentDao.getById(comment.getParentCommentId());
        if (parent == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "父评论不存在：" + comment.getParentCommentId());
        }
        return parent.getUserId();
    }
}
