package ink.whi.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.repo.entity.UserFootDO;


public interface UserFootMapper extends BaseMapper<UserFootDO> {
    VideoFootCountDTO countVideoInfoByVideoId(Long videoId);

    VideoFootCountDTO countVideoByUserId(Long userId);
}
