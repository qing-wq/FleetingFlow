package ink.whi.comment.controller;

import ink.whi.comment.dto.TopCommentDTO;
import ink.whi.comment.model.req.CommentSaveReq;
import ink.whi.comment.service.CommentReadService;
import ink.whi.comment.service.CommentWriteService;
import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import ink.whi.notify.constants.VideoMqConstants;
import ink.whi.user.client.UserClient;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 评论接口
 *
 * @author: qing
 * @Date: 2023/5/2
 */
@RestController
@RequestMapping(path = "comment/api")
public class CommentRestController extends BaseRestController {

    @Autowired
    private CommentReadService commentReadService;

    @Autowired
    private CommentWriteService commentWriteService;

    @Resource
    private UserClient userClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 评论列表分页接口
     *
     * @param videoId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(path = "page")
    public ResVo<PageListVo<TopCommentDTO>> list(@RequestParam(name = "videoId") Long videoId,
                                                 @RequestParam(name = "page") Long page,
                                                 @RequestParam Long pageSize) {
        PageParam pageParam = buildPageParam(page, pageSize);
        PageListVo<TopCommentDTO> vo = commentReadService.queryVideoComments(videoId, pageParam);
        return ResVo.ok(vo);
    }

    /**
     * 发布评论
     *
     * @param req
     * @return commentId
     */
    @Permission(role = UserRole.LOGIN)
    @PostMapping(path = "post")
    public ResVo<Long> save(@RequestBody CommentSaveReq req) {
        if (req.getVideoId() == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文章id为空");
        }
        req.setUserId(ReqInfoContext.getReqInfo().getUserId());
        req.setCommentContent(StringEscapeUtils.escapeHtml3(req.getCommentContent()));  // 替换html中特殊符号
        Long commentId = commentWriteService.saveComment(req);
        return ResVo.ok(commentId);
    }

    /**
     * 评论点赞、取消点赞等操作
     *
     * @param commentId
     * @param operateType 2-点赞 4-取消点赞
     * @return
     */
    @GetMapping(path = "favor")
    @Permission(role = UserRole.LOGIN)
    public ResVo<String> favor(@RequestParam(name = "commentId") Long commentId,
                               @RequestParam(name = "type") Integer operateType) {
        CommentDTO comment = commentReadService.queryComment(commentId);
        if (comment == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "评论不存在：" + commentId);
        }

        OperateTypeEnum type = OperateTypeEnum.fromCode(operateType);
        if (type != OperateTypeEnum.PRAISE && type != OperateTypeEnum.CANCEL_PRAISE) {
            // 评论只能进行点赞操作
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "操作非法: " + operateType);
        }
        UserFootDTO foot = userClient.saveUserFoot(VideoTypeEnum.COMMENT.getCode(), commentId, comment.getUserId(), ReqInfoContext.getReqInfo().getUserId(), type.getCode());

        NotifyTypeEnum notifyType = OperateTypeEnum.getNotifyType(type);

        // 将评论的relatedId设为视频id
        foot.setVideoId(comment.getVideoId());
        Optional.ofNullable(notifyType).ifPresent(s -> rabbitTemplate.convertAndSend(VideoMqConstants.VIDEO_TOPIC_EXCHANGE,
                s == NotifyTypeEnum.PRAISE ? VideoMqConstants.VIDEO_PRAISE_KEY : VideoMqConstants.VIDEO_CANCEL_PRAISE_KEY, foot));
        return ResVo.ok("ok");
    }
}
