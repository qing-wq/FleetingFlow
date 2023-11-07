package ink.whi.user.service.user;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.FollowStateEnum;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.model.dto.BaseUserDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.req.UserSaveReq;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.dao.UserDao;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.repo.entity.UserDO;
import ink.whi.user.repo.entity.UserInfoDO;
import ink.whi.user.repo.entity.UserRelationDO;
import ink.whi.user.service.CountReadService;
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
    private CountReadService countService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BaseUserDTO queryUserByUserId(Long userId) {
        UserDO user = userDao.getUser(userId);
        return UserConverter.toDTO(user);
    }

    @Override
    public BaseUserInfoDTO passwordLogin(String username, String password) {
        UserDO user = userDao.getUserByNameOrEmail(username);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.USER_NOT_EXISTS, username);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw BusinessException.newInstance(StatusEnum.USER_PWD_ERROR);
        }
        return queryBasicUserInfo(user.getId());
    }

    @Override
    public BaseUserInfoDTO queryBasicUserInfo(Long userId) {
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.USER_NOT_EXISTS, userId);
        }
        return UserConverter.toDTO(user);
    }

    @Override
    public UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId) {
        BaseUserInfoDTO userInfoDTO = queryBasicUserInfo(userId);
        UserStatisticInfoDTO userHomeDTO = UserConverter.toUserHomeDTO(userInfoDTO);

        // 用户计数信息
        UserStatisticInfoDTO videoFootCount = countService.queryUserStatisticInfo(userId);
        System.out.println(videoFootCount + "=================");
        if (videoFootCount != null) {
            userHomeDTO.setPraiseCount(videoFootCount.getPraiseCount());
            userHomeDTO.setCollectionCount(videoFootCount.getCollectionCount());
            userHomeDTO.setVideoCount(videoFootCount.getVideoCount());
            userHomeDTO.setFansCount(videoFootCount.getFansCount());
            userHomeDTO.setCollectionCount(videoFootCount.getCollectionCount());
            userHomeDTO.setFollowCount(videoFootCount.getFollowCount());
        } else {
            userHomeDTO.setPraiseCount(0);
            userHomeDTO.setCollectionCount(0);
            userHomeDTO.setVideoCount(0);
            userHomeDTO.setFansCount(0);
            userHomeDTO.setCollectionCount(0);
            userHomeDTO.setFollowCount(0);
        }

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
        UserDO record = userDao.getUserByNameOrEmail(user.getUserName());
        if (record != null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "用户已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
        UserInfoDO userInfo = UserConverter.toUserInfoDo(req);
        userInfo.setUserId(user.getId());
        // 生成默认初始用户名
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

    @Override
    public void updateUserPwd(Long userId, String olderPwd, String newPassword) {
        UserDO user = userDao.getUser(userId);
        if (Objects.equals(user.getPassword(), passwordEncoder.encode(olderPwd))) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.saveUser(user);
            return;
        }
        throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS, "密码错误");
    }

    @Override
    public SimpleUserInfoDTO querySimpleUserInfo(Long userId) {
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.USER_NOT_EXISTS);
        }
        return UserConverter.toSimpleUserDTO(user);
    }

    @Override
    public BaseUserDTO queryBasicUser(Long userId) {
        return null;
    }
}
