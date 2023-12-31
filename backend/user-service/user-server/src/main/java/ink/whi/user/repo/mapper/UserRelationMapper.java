package ink.whi.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.repo.entity.UserRelationDO;

import java.util.List;


public interface UserRelationMapper extends BaseMapper<UserRelationDO> {
    List<FollowUserInfoDTO> queryUserFansList(Long userId, PageParam pageParam);

    List<FollowUserInfoDTO> queryUserFollowList(Long followUserId, PageParam pageParam);
}
