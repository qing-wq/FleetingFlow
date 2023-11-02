package ink.whi.user.controller;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.UserFootService;
import ink.whi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * feign 远程调用接口
 *
 * @author: qing
 * @Date: 2023/10/27
 */
@RestController
@RequestMapping(path = "client")
public class UserClientRestController {

    @Autowired
    private UserFootService userFootService;

    @Autowired
    private UserService userService;

    /**
     * 保存/更新用户足迹
     *
     * @param videoTypeEnum VideoTypeEnum
     * @param videoId
     * @param author
     * @param userId
     * @param operateTypeEnum
     * @return
     */
    @PostMapping(path = "foot/update")
    public UserFootDTO saveUserFoot(Integer videoTypeEnum, Long videoId, Long author, Long userId, Integer operateTypeEnum) {
        VideoTypeEnum type = VideoTypeEnum.formCode(videoTypeEnum);
        OperateTypeEnum operate = OperateTypeEnum.fromCode(operateTypeEnum);
        UserFootDO foot = userFootService.saveOrUpdateUserFoot(type, videoId, author, userId, operate);
        return UserConverter.toDTO(foot);
    }

    @GetMapping(path = "foot/comment")
    UserFootDTO saveCommentFoot(@RequestParam CommentDTO comment, @RequestParam Long userId, @RequestParam Long parentCommentUser) {

    }

    @GetMapping(path = "base/{userId}")
    public BaseUserInfoDTO queryBasicUserInfo(@PathVariable Long userId) {
        return userService.queryBasicUserInfo(userId);
    }

    @GetMapping(path = "simple/{userId}")
    public SimpleUserInfoDTO querySimpleUserInfo(@PathVariable Long userId) {
        return userService.querySimpleUserInfo(userId);
    }
}
