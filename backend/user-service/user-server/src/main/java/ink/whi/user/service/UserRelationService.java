package ink.whi.user.service;

import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.model.req.UserRelationReq;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
public interface UserRelationService {

    PageListVo<FollowUserInfoDTO> getUserFansList(Long userId, PageParam pageParam);

    PageListVo<FollowUserInfoDTO> getUserFollowList(Long userId, PageParam pageParam);

    void saveUserRelation(UserRelationReq req);

    void updateUserFollowRelationId(PageListVo<FollowUserInfoDTO> followList, Long loginUserId);
}
