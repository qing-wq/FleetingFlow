package ink.whi.user.service.count;

import ink.whi.cache.redis.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.service.CountReadService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
@Service
public class CountReadServiceImpl implements CountReadService {

    /**
     * 获取用户全部计数信息
     * @param userId
     * @return
     */
    @Override
    public UserStatisticInfoDTO queryUserStatisticInfo(Long userId) {
        Map<String, Integer> resutMap = RedisClient.hGetAll(CountConstants.USER_STATISTIC + userId, Integer.class);
        UserStatisticInfoDTO dto = new UserStatisticInfoDTO();
        dto.setFollowCount(resutMap.get(CountConstants.FOLLOW_COUNT));
        dto.setFansCount(resutMap.get(CountConstants.FANS_COUNT));
        dto.setVideoCount(resutMap.get(CountConstants.VIDEO_COUNT));
        dto.setPraiseCount(resutMap.get(CountConstants.PRAISE_COUNT));
        dto.setTotalReadCount(resutMap.get(CountConstants.VIEW_COUNT));
        return dto;
    }

}
