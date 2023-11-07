package ink.whi.user.controller;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.CountService;
import ink.whi.user.service.UserFootService;
import ink.whi.user.service.UserService;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private CountService countService;

    /**
     * 保存/更新用户足迹
     *
     * @param videoType VideoTypeEnum
     * @param videoId
     * @param author
     * @param userId
     * @param operate   OperateTypeEnum
     * @return
     */
    @GetMapping(path = "foot/user")
    @Permission(role = UserRole.LOGIN)
    public UserFootDTO saveUserFoot(@RequestParam Integer videoType, @RequestParam Long videoId,
                                    @RequestParam Long author, @RequestParam Long userId, @RequestParam Integer operate) {
        VideoTypeEnum videoEnum = VideoTypeEnum.formCode(videoType);
        OperateTypeEnum operateEnum = OperateTypeEnum.fromCode(operate);
        UserFootDO foot = userFootService.saveOrUpdateUserFoot(videoEnum, videoId, author, userId, operateEnum);
        return UserConverter.toDTO(foot);
    }

    /**
     * 保存/更新评论足迹
     *
     * @param comment
     * @param userId
     * @param parentCommentUser
     * @return
     */
    @PostMapping(path = "foot/comment")
    public void saveCommentFoot(@RequestBody CommentDTO comment, @RequestParam Long userId,
                                @RequestParam(required = false) Long parentCommentUser) {
        userFootService.saveCommentFoot(comment, userId, parentCommentUser);
    }

    /**
     * 获取当前用户点赞状态
     *
     * @param commentId
     * @param loginUserId
     * @return
     */
    @GetMapping(path = "foot/praise")
    UserFootDTO queryUserFoot(Long commentId, Integer type, Long loginUserId) {
         return userFootService.queryUserFoot(commentId, type, loginUserId);
    }


    @GetMapping(path = "base/{userId}")
    public BaseUserInfoDTO queryBasicUserInfo(@PathVariable Long userId) {
        return userService.queryBasicUserInfo(userId);
    }

    @GetMapping(path = "simple/{userId}")
    public SimpleUserInfoDTO querySimpleUserInfo(@PathVariable Long userId) {
        return userService.querySimpleUserInfo(userId);
    }

    /**
     * 获取评论点赞数
     *
     * @param commentId
     * @return
     */
    @GetMapping(path = "comment/{commentId}")
    public Integer queryCommentPraiseCount(@PathVariable Long commentId){
        return countService.queryCommentPraiseCount(commentId);
    }

    /**
     * 查询用户阅读记录
     * @param userId
     * @param pageParam
     * @return
     */
    @GetMapping(path = "client/foot/read")
    List<Long> queryUserReadVideoList(@RequestParam Long userId, PageParam pageParam) {
        return userFootService.queryUserReadVideoList(userId, pageParam);
    }

    /**
     * 查询用户收藏记录
     * @param userId
     * @param pageParam
     * @return
     */
    @GetMapping(path = "client/foot/collect")
    List<Long> queryUserCollectionVideoList(@RequestParam Long userId, PageParam pageParam) {
        return userFootService.queryUserCollectionVideoList(userId, pageParam);
    }
}
