package ink.whi.user.client;

import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.SimpleUserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@FeignClient(value = "user-service", fallback = UserClientResolver.class)
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "client/base/{userId}")
    BaseUserDTO queryBasicUserInfo(@PathVariable Long userId);

    @GetMapping(path = "client/simple/{userId}")
    SimpleUserInfoDTO querySimpleUserInfo(@PathVariable Long userId);
}
