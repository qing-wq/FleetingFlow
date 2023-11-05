package ink.whi.user.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.FollowTypeEnum;
import ink.whi.common.enums.HomeSelectEnum;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.video.client.VideoClient;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.vo.UserHomeVo;
import ink.whi.user.service.UserRelationService;
import ink.whi.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * 用户接口
 *
 * @author: qing
 * @Date: 2023/10/24
 */
@RestController
@RequestMapping(path = "user")
public class UserRestController extends BaseRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService userRelationService;

    @Resource
    private VideoClient videoClient;

    /**
     * 用户主页接口
     *
     * @param userId         如果不填默认为当前登录用户主页
     * @param homeSelectType 主页选择标签，默认works
     * @return
     */
    @GetMapping(path = "/{userId}")
    public ResVo<UserHomeVo> getUserHome(@PathVariable(name = "userId", required = false) Long userId,
                                         @RequestParam(name = "homeSelectType", required = false) String homeSelectType) {
        PageParam pageParam = PageParam.newPageInstance();
        UserHomeVo vo = initUserHomeVo(userId, homeSelectType, pageParam);

        // 用户信息
        UserStatisticInfoDTO userInfo = userService.queryUserInfoWithStatistic(userId);
        vo.setUserHome(userInfo);

        // 视频列表
        PageListVo<VideoInfoDTO> videos = videoClient.listVideosByUserId(userId, pageParam);
        vo.setHomeSelectList(videos);

        return ResVo.ok(vo);
    }

    /**
     * 用户主页分页接口
     *
     * @param userId         如果不填默认为当前登录用户主页
     * @param homeSelectType 主页选择标签，默认works
     * @return
     */
    @GetMapping(path = "page")
    public ResVo<UserHomeVo> page(@RequestParam(name = "userId", required = false) Long userId,
                                  @RequestParam(name = "homeSelectType", required = false) String homeSelectType,
                                  @RequestParam("page") Long page,
                                  @RequestParam(name = "pageSize", required = false) Long pageSize) {
        PageParam pageParam = buildPageParam(page, pageSize);
        UserHomeVo vo = initUserHomeVo(userId, homeSelectType, pageParam);
        return ResVo.ok(vo);
    }

    private UserHomeVo initUserHomeVo(Long userId, String homeSelectType, PageParam pageParam) {
        UserHomeVo vo = new UserHomeVo();
        vo.setHomeSelectType(StringUtils.isBlank(homeSelectType) ? HomeSelectEnum.WORKS.getCode() : homeSelectType);

        if (userId == null) {
            userId = ReqInfoContext.getReqInfo().getUserId();
        }
        userHomeSelectList(vo, userId, pageParam);
        return vo;
    }

    /**
     * 返回选择列表
     *
     * @param vo
     * @param userId
     */
    private void userHomeSelectList(UserHomeVo vo, Long userId, PageParam pageParam) {
        HomeSelectEnum select = HomeSelectEnum.fromCode(vo.getHomeSelectType());
        if (select == null) {
            return;
        }

        switch (select) {
            case WORKS:
            case READ:
            case COLLECTION:
//                PageListVo<VideoInfoDTO> dto = videoReadService.queryVideosByUserAndType(userId, pageParam, select);
//                vo.setHomeSelectList(dto);
                return;
            case LIKE:
                vo.setHomeSelectList(PageListVo.emptyVo());
//                initFollowFansList(vo, userId, pageParam);
                return;
            default:
        }
    }

    /**
     * 获取粉丝/关注列表
     *
     * @param userId
     * @param type follow-关注，fans-粉丝
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(path = "follow/{userId}")
    public ResVo<PageListVo<FollowUserInfoDTO>> initFollowFansList(@PathVariable(name = "userId") Long userId,
                                                                   @RequestParam(name = "type", required = false) String type,
                                                                   @RequestParam("page") Long page,
                                                                   @RequestParam(name = "pageSize", required = false) Long pageSize) {
        PageListVo<FollowUserInfoDTO> followList;
        PageParam pageParam = buildPageParam(page, pageSize);
        boolean needUpdateRelation = false;
        if (type.equals(FollowTypeEnum.FOLLOW.getCode())) {
            followList = userRelationService.getUserFollowList(userId, pageParam);
        } else {
            followList = userRelationService.getUserFansList(userId, pageParam);
            needUpdateRelation = true;
        }

        Long loginUserId = ReqInfoContext.getReqInfo().getUserId();
        if (!Objects.equals(loginUserId, userId) || needUpdateRelation) {
            userRelationService.updateUserFollowRelationId(followList, loginUserId);
        }
        return ResVo.ok(followList);
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    @Permission(role = UserRole.LOGIN)
    public ResVo<BaseUserInfoDTO> info() {
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        BaseUserInfoDTO info = userService.queryBasicUserInfo(userId);
        return ResVo.ok(info);
    }

    /**
     * 保存用户信息
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Permission(role = UserRole.LOGIN)
    @PostMapping(path = "saveInfo")
    public ResVo<Boolean> saveUserInfo(@RequestBody UserInfoSaveReq req) {
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        req.setUserId(userId);
        userService.saveUserInfo(req);
        return ResVo.ok(true);
    }

    /**
     * 更改密码
     *
     * @param olderPassword
     * @param newPassword
     * @return
     */
    @PostMapping(path = "update")
    public ResVo<String> updatePassword(@RequestParam(name = "old") String olderPassword,
                                        @RequestParam(name = "new") String newPassword) {
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        userService.updateUserPwd(userId, olderPassword, newPassword);
        return ResVo.ok("ok");
    }
}