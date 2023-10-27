package ink.whi.user.client;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.vo.dto.UserFootDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@FeignClient(value = "user-service", fallback = UserFootClient.class)
public interface UserFootClient {

    @PostMapping(path = "client/foot/update")
    UserFootDTO saveUserFoot(VideoTypeEnum type, Long videoId, Long author, Long userId, OperateTypeEnum operateTypeEnum);
}
