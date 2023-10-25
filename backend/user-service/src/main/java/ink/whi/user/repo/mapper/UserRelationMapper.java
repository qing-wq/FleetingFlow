package ink.whi.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.repo.entity.UserRelationDO;
import ink.whi.user.model.dto.FollowUserInfoDTO;

import java.util.List;


public interface UserRelationMapper extends BaseMapper<UserRelationDO> {
    List<FollowUserInfoDTO> queryUserFansList(Long userId, PageParam pageParam);

    List<FollowUserInfoDTO> queryUserFollowList(Long followUserId, PageParam pageParam);
}
