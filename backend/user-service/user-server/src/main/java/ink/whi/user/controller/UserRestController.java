package ink.whi.user.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.HomeSelectEnum;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.permission.Permission;
import ink.whi.common.permission.UserRole;
import ink.whi.common.utils.JwtUtil;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.req.UserRelationReq;
import ink.whi.user.model.req.UserSaveReq;
import ink.whi.user.model.vo.UserHomeVo;
import ink.whi.user.service.UserRelationService;
import ink.whi.user.service.UserService;
import ink.whi.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.apache.http.nio.reactor.ssl.SSLIOSession.SESSION_KEY;


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

    /**
     * 用户主页接口
     *
     * @param userId           如果不填默认为当前登录用户主页
     * @param homeSelectType   主页选择标签，默认works
     * @return
     */
    @GetMapping(path = "/{userId}")
    public ResVo<UserHomeVo> getUserHome(@PathVariable(name = "userId", required = false) Long userId,
                                         @RequestParam(name = "homeSelectType", required = false) String homeSelectType) {
        UserHomeVo vo = initUserHomeVo(userId, homeSelectType, PageParam.newPageInstance());

        UserStatisticInfoDTO userInfo = userService.queryUserInfoWithStatistic(userId);
        vo.setUserHome(userInfo);
        return ResVo.ok(vo);
    }

    /**
     * 用户主页分页接口
     *
     * @param userId           如果不填默认为当前登录用户主页
     * @param homeSelectType   主页选择标签，默认works
     * @return
     */
    @GetMapping(path = "list")
    public ResVo<UserHomeVo> page(@RequestParam(name = "userId", required = false) Long userId,
                                  @RequestParam(name = "homeSelectType", required = false) String homeSelectType,
                                  @RequestParam("page") Long page,
                                  @RequestParam(name = "pageSize", required = false) Long pageSize) {
        PageParam pageParam = buildPageParam(page, pageSize);
        UserHomeVo vo = initUserHomeVo(userId, homeSelectType,  pageParam);
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
//                PageListVo<VideoInfoDTO> dto = articleReadService.queryArticlesByUserAndType(userId, pageParam, select);
//                vo.setHomeSelectList(dto);
                return;
            case LIKE:
                vo.setHomeSelectList(PageListVo.emptyVo());
//                initFollowFansList(vo, userId, pageParam);
                return;
            default:
        }
    }

//    private void initFollowFansList(UserHomeVo vo, Long userId, PageParam pageParam) {
//        PageListVo<FollowUserInfoDTO> followList;
//        boolean needUpdateRelation = false;
//        if (vo.getFollowSelectType().equals(FollowTypeEnum.FOLLOW.getCode())) {
//            followList = userRelationService.getUserFollowList(userId, pageParam);
//        } else {
//            // 查询粉丝列表时，只能确定粉丝关注了userId，但是不能反向判断，因此需要再更新下映射关系，判断userId是否有关注这个用户
//            followList = userRelationService.getUserFansList(userId, pageParam);
//            needUpdateRelation = true;
//        }
//
//        Long loginUserId = ReqInfoContext.getReqInfo().getUserId();
//        if (!Objects.equals(loginUserId, userId) || needUpdateRelation) {
//            userRelationService.updateUserFollowRelationId(followList, loginUserId);
//        }
//        vo.setFollowList(followList);
//    }

    /**
     * 用户注册
     * @param req
     * @return
     */
    @PostMapping(path = "register")
    public ResVo<Long> register(@RequestBody UserSaveReq req, HttpServletResponse response) {
        if (StringUtils.isBlank(req.getUsername()) || StringUtils.isBlank(req.getPassword())) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "账号密码不能为空");
        }
        Long userId = userService.saveUser(req);
        // 签发token
        String token = JwtUtil.createToken(userId);
        if (StringUtils.isBlank(token)) {
            return ResVo.fail(StatusEnum.TOKEN_NOT_EXISTS);
        }
        response.addCookie(SessionUtil.newCookie(SESSION_KEY, token));
        return ResVo.ok(userId);
    }

    /**
     * 用户关注
     *
     * @param req
     * @return
     * @throws Exception
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
        return ResVo.ok(true);
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