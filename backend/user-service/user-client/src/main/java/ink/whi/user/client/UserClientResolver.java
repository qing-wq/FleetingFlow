package ink.whi.user.client;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.SimpleUserInfoDTO;
import ink.whi.common.vo.dto.UserFootDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	@Override
	public BaseUserDTO queryBasicUserInfo(Long userId) {
		log.error("User 服务异常：queryBasicUserInfo 请求失败");
		return null;
	}

	@Override
	public SimpleUserInfoDTO querySimpleUserInfo(Long userId) {
		log.error("User 服务异常：querySimpleUserInfo 请求失败");
		return null;
	}

	@Override
	public UserFootDTO saveUserFoot(Integer videoTypeEnum, Long videoId, Long author, Long userId, Integer operateTypeEnum) {
		log.error("User 服务异常：saveUserFoot 请求失败");
		return null;
	}
}
