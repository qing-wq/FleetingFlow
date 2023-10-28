package ink.whi.user.client;

import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.SimpleUserInfoDTO;
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
}
