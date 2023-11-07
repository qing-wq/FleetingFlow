package ink.whi.user.repo.converter;

import ink.whi.common.enums.LoginTypeEnum;
import ink.whi.common.enums.RoleEnum;
import ink.whi.common.model.dto.BaseUserDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.req.UserInfoSaveReq;
import ink.whi.user.model.req.UserSaveReq;
import ink.whi.user.repo.entity.UserDO;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.repo.entity.UserInfoDO;
import org.springframework.beans.BeanUtils;

/**
 * 实体转换
 */
public class UserConverter {

    public static BaseUserDTO toDTO(UserDO user) {
        if (user == null) {
            return null;
        }

        BaseUserDTO dto = new BaseUserDTO();
        BeanUtils.copyProperties(user, user);
        return dto;
    }

    public static BaseUserInfoDTO toDTO(UserInfoDO info) {
        if (info == null) {
            return null;
        }
        BaseUserInfoDTO user = new BaseUserInfoDTO();
        BeanUtils.copyProperties(info, user);
        user.setRole(RoleEnum.role(info.getUserRole()));
        return user;
    }

    public static UserStatisticInfoDTO toUserHomeDTO(BaseUserInfoDTO baseUserInfoDTO) {
        if (baseUserInfoDTO == null) {
            return null;
        }
        UserStatisticInfoDTO userHomeDTO = new UserStatisticInfoDTO();
        BeanUtils.copyProperties(baseUserInfoDTO, userHomeDTO);
        return userHomeDTO;
    }

    public static UserDO toUserDo(UserSaveReq req) {
        UserDO user = new UserDO();
        user.setUserName(req.getUsername());
        user.setPassword(req.getPassword());
        user.setLoginType(LoginTypeEnum.PASSWORD_LOGIN.getCode());
        return user;
    }

    public static UserInfoDO toUserInfoDo(UserSaveReq req) {
        if (req == null) {
            return null;
        }
        UserInfoDO info = new UserInfoDO();
        BeanUtils.copyProperties(req, info);
        return info;
    }

    public static UserInfoDO toDo(UserInfoSaveReq req) {
        if (req == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setUserName(req.getUserName());
        BeanUtils.copyProperties(req, userInfoDO);
        return userInfoDO;
    }

    public static UserFootDTO toDTO(UserFootDO foot) {
        if (foot == null) {
            return null;
        }
        UserFootDTO dto = new UserFootDTO();
        BeanUtils.copyProperties(foot, dto);
        return dto;
    }

    public static SimpleUserInfoDTO toSimpleUserDTO(UserInfoDO user) {
        if (user == null) {
            return null;
        }
        SimpleUserInfoDTO dto = new SimpleUserInfoDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
