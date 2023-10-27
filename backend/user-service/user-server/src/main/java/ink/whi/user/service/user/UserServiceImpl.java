package ink.whi.user.service.user;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.FollowStateEnum;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.req.UserSaveReq;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.dao.UserDao;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.repo.entity.UserDO;
import ink.whi.user.repo.entity.UserInfoDO;
import ink.whi.user.repo.entity.UserRelationDO;
import ink.whi.user.service.CountService;
import ink.whi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRelationDao userRelationDao;

    @Autowired
    private CountService countService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BaseUserDTO queryUserByUserId(Long userId) {
        UserDO user = userDao.getUser(userId);
        return UserConverter.toDTO(user);
    }

    @Override
    public BaseUserInfoDTO passwordLogin(String username, String password) {
        UserDO user = userDao.getUserByName(username);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.USER_NOT_EXISTS, username);
        }

        // 密码加密
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw BusinessException.newInstance(StatusEnum.USER_PWD_ERROR);
        }
        return queryBasicUserInfo(user.getId());
    }

    @Override
    public BaseUserInfoDTO queryBasicUserInfo(Long userId) {
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.USER_NOT_EXISTS, "userId=" + userId);
        }
        return UserConverter.toDTO(user);
    }

    @Override
    public UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId) {
        BaseUserInfoDTO userInfoDTO = queryBasicUserInfo(userId);
        UserStatisticInfoDTO userHomeDTO = UserConverter.toUserHomeDTO(userInfoDTO);

        // 获取视频相关统计
        // todo：换成redis
        VideoFootCountDTO videoFootCount = countService.queryArticleCountInfoByUserId(userId);
        if (videoFootCount != null) {
            userHomeDTO.setPraiseCount(videoFootCount.getPraiseCount());
            userHomeDTO.setCollectionCount(videoFootCount.getCollectionCount());
        } else {
            userHomeDTO.setPraiseCount(0);
            userHomeDTO.setCollectionCount(0);
        }

        // todo: 获取发布视频总数
//        int articleCount = articleReadService.queryArticleCount(userId);
//        userHomeDTO.setArticleCount(articleCount);

        // 粉丝数
        Integer fansCount = userRelationDao.queryUserFansCount(userId);
        userHomeDTO.setFansCount(fansCount);

        // 关注人数
        int followsCount = userRelationDao.queryUserFollowsCount(userId);
        userHomeDTO.setFollowCount(followsCount);

        // 是否关注
        Long followUserId = ReqInfoContext.getReqInfo().getUserId();
        if (followUserId != null) {
            UserRelationDO userRelationDO = userRelationDao.getUserRelationByUserId(userId, followUserId);
            userHomeDTO.setFollowed(userRelationDO != null && Objects.equals(userRelationDO.getFollowState(), FollowStateEnum.FOLLOW.getCode()));

        } else {
            userHomeDTO.setFollowed(Boolean.FALSE);
        }
        return userHomeDTO;
    }

    /**
     * 创建用户
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(UserSaveReq req) {
        UserDO user = UserConverter.toUserDo(req);
        UserDO record = userDao.getUserByName(user.getUserName());
        if (record != null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "用户已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
        UserInfoDO userInfo = UserConverter.toUserInfoDo(req);
        userInfo.setUserId(user.getId());
        userInfo.setUserName("默认用户" + UUID.randomUUID());
        userDao.save(userInfo);
        return user.getId();
    }

    @Override
    public void saveUserInfo(UserInfoSaveReq req) {
        UserInfoDO info = UserConverter.toDo(req);
        UserInfoDO record = userDao.getByUserId(req.getUserId());
        info.setId(record.getId());
        userDao.updateById(info);
    }
}
