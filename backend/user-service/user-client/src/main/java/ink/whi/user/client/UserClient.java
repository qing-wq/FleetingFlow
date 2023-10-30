package ink.whi.user.client;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.SimpleUserInfoDTO;
import ink.whi.common.vo.dto.UserFootDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "client/foot/update")
    UserFootDTO saveUserFoot(@RequestParam Integer videoTypeEnum, @RequestParam Long videoId, @RequestParam Long author, @RequestParam Long userId, @RequestParam Integer operateTypeEnum);
}
