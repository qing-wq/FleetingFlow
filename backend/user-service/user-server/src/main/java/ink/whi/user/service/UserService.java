package ink.whi.user.service;


import ink.whi.common.model.dto.BaseUserDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.req.UserSaveReq;

public interface UserService {
    BaseUserDTO queryUserByUserId(Long userId);

    BaseUserInfoDTO passwordLogin(String username, String password);

    BaseUserInfoDTO queryBasicUserInfo(Long userId);

    UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId);

    Long saveUser(UserSaveReq req);

    void saveUserInfo(UserInfoSaveReq req);

    void updateUserPwd(Long userId, String olderPassword, String newPassword);

    SimpleUserInfoDTO querySimpleUserInfo(Long userId);

    BaseUserDTO queryBasicUser(Long userId);
}
