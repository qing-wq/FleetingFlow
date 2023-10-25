package ink.whi.user.client;

import ink.whi.common.vo.dto.BaseUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/12 9:56
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	@Override
	public BaseUserDTO queryBasicUserInfo(Long userId) {
		log.error("User 服务异常：getByUserId 请求失败");
		return null;
	}
}
