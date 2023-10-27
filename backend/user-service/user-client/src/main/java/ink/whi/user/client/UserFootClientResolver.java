package ink.whi.user.client;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.vo.dto.UserFootDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Slf4j
@Component
public class UserFootClientResolver implements UserFootClient{

    @Override
    public UserFootDTO saveUserFoot(VideoTypeEnum type, Long articleId, Long author, Long userId, OperateTypeEnum operateTypeEnum) {
        log.error("User 服务异常：saveUserFoot 请求失败");
        return null;
    }
}
