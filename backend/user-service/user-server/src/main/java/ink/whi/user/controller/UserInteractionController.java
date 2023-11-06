package ink.whi.user.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.notify.constants.VideoMqConstants;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.req.UserRelationReq;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.CountService;
import ink.whi.user.service.UserFootService;
import ink.whi.user.service.UserRelationService;
import ink.whi.user.service.UserService;
import ink.whi.video.client.VideoClient;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户交互接口
 *
 * @author: qing
 * @Date: 2023/10/29
 */
@RestController
@RequestMapping(path = "foot")
public class UserInteractionController extends BaseRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFootService userFootService;

    @Resource
    private VideoClient videoClient;

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private CountService countService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 视频点赞、收藏相关操作
     *
     * @param videoId
     * @param operateType 2-点赞 3-收藏 4-取消点赞 5-取消收藏
     * @return
     */
    @Permission(role = UserRole.LOGIN)
    @GetMapping(path = "favor")
    public ResVo<Boolean> favor(@RequestParam(name = "videoId") Long videoId,
                                @RequestParam(name = "operate") Integer operateType) {
        OperateTypeEnum type = OperateTypeEnum.fromCode(operateType);
        if (type == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "参数非法：" + operateType);
        }

        SimpleVideoInfoDTO video = videoClient.queryBasicVideoInfo(videoId);
        if (video == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "视频不存在");
        }

        countService.incrVideoStatisticCount(videoId, video.getAuthorId(), type);
        UserFootDO foot = userFootService.saveOrUpdateUserFoot(VideoTypeEnum.VIDEO, videoId, video.getAuthorId(),
                ReqInfoContext.getReqInfo().getUserId(), type);

        // 消息通知
        NotifyTypeEnum notifyType = OperateTypeEnum.getNotifyType(type);
        Optional.ofNullable(notifyType).ifPresent(s -> rabbitTemplate.convertAndSend(VideoMqConstants.VIDEO_TOPIC_EXCHANGE,
                s == NotifyTypeEnum.PRAISE ? VideoMqConstants.VIDEO_PRAISE_KEY : VideoMqConstants.VIDEO_CANCEL_PRAISE_KEY, foot));
        return ResVo.ok(true);
    }

    /**
     * 用户关注
     *
     * @param req
     * @return
     */
    @Permission(role = UserRole.LOGIN)
    @PostMapping(path = "follow")
    public ResVo<Boolean> saveUserRelation(@RequestBody UserRelationReq req) {
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        // 校验关注的用户是否存在
        BaseUserInfoDTO userInfo = userService.queryBasicUserInfo(req.getUserId());
        req.setFollowUserId(userId);
        if (Objects.equals(req.getUserId(), userId)) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "操作非法：不允许关注自己");
        }
        userRelationService.saveUserRelation(req);

        // todo：消息通知
        return ResVo.ok(true);
    }
}
