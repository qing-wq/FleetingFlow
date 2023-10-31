package ink.whi.user.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import ink.whi.user.rabbitmq.VideoMqConstants;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.UserFootService;
import ink.whi.user.service.UserService;
import ink.whi.video.client.VideoClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 视频查询接口
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

//    @Autowired
//    private CommentReadService commentReadService;

    @Resource
    private VideoClient videoClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 视频点赞、收藏相关操作
     *
     * @param videoId
     * @param operateType 2-点赞 3-收藏 4-取消点赞 5-取消收藏
     * @return
     */
//    @Permission(role = UserRole.LOGIN)
    @GetMapping(path = "favor")
    public ResVo<Boolean> favor(@RequestParam(name = "videoId") Long videoId,
                                @RequestParam(name = "operate") Integer operateType) {
        OperateTypeEnum type = OperateTypeEnum.fromCode(operateType);
        if (type == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "参数非法：" + operateType);
        }

        SimpleVideoInfoDTO video = videoClient.queryBasicVideoInfo(videoId);

        UserFootDO foot = userFootService.saveOrUpdateUserFoot(VideoTypeEnum.VIDEO, videoId, video.getUserId(),
                ReqInfoContext.getReqInfo().getUserId(), type);

        // 消息通知
        NotifyTypeEnum notifyType = OperateTypeEnum.getNotifyType(type);
        Optional.ofNullable(notifyType).ifPresent(s -> rabbitTemplate.convertAndSend(VideoMqConstants.VIDEO_TOPIC_EXCHANGE,
                s == NotifyTypeEnum.PRAISE ? VideoMqConstants.VIDEO_PRAISE_KEY : VideoMqConstants.VIDEO_CANCEL_PRAISE_KEY, foot));
        return ResVo.ok(true);
    }
}
