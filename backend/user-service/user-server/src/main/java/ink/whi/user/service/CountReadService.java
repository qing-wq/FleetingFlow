package ink.whi.user.service;

import ink.whi.user.model.dto.UserStatisticInfoDTO;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
public interface CountReadService {
    UserStatisticInfoDTO queryUserStatisticInfo(Long userId);
}
