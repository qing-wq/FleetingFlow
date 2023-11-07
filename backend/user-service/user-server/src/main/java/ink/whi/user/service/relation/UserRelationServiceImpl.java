package ink.whi.user.service.relation;

import ink.whi.common.enums.FollowStateEnum;
import ink.whi.common.utils.MapUtils;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.notify.constants.UserMqConstants;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.model.req.UserRelationReq;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.repo.entity.UserRelationDO;
import ink.whi.user.service.UserRelationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationDao userRelationDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 获取用户关注列表
     * @param userId
     * @param pageParam
     * @return
     */
    @Override
    public PageListVo<FollowUserInfoDTO> getUserFollowList(Long userId, PageParam pageParam) {
        List<FollowUserInfoDTO> userRelationList = userRelationDao.listUserFollows(userId, pageParam);
        return PageListVo.newVo(userRelationList, pageParam.getPageSize());
    }

    /**
     * 获取用户粉丝列表
     * @param userId
     * @param pageParam
     * @return
     */
    @Override
    public PageListVo<FollowUserInfoDTO> getUserFansList(Long userId, PageParam pageParam) {
        List<FollowUserInfoDTO> userRelationList = userRelationDao.listUserFans(userId, pageParam);
        return PageListVo.newVo(userRelationList, pageParam.getPageSize());
    }


    /**
     * 保存用户关系
     * @param req
     */
    @Override
    public void saveUserRelation(UserRelationReq req) {
        UserRelationDO record = userRelationDao.getUserRelationByUserId(req.getUserId(), req.getFollowUserId());
        FollowStateEnum state = req.getFollowed() ? FollowStateEnum.FOLLOW : FollowStateEnum.CANCEL_FOLLOW;
        if (record == null) {
            record = new UserRelationDO().setUserId(req.getUserId()).setFollowUserId(req.getFollowUserId()).setFollowState(state.getCode());
            userRelationDao.save(record);
            // 发布消息
            rabbitTemplate.convertAndSend(UserMqConstants.USER_TOPIC_EXCHANGE, UserMqConstants.USER_FOLLOW_KEY, UserConverter.toUserRelationDto(record));
            return;
        }

        record.setFollowState(state.getCode());
        userRelationDao.updateById(record);
        rabbitTemplate.convertAndSend(UserMqConstants.USER_TOPIC_EXCHANGE, UserMqConstants.USER_CANCEL_FOLLOW_KEY, UserConverter.toUserRelationDto(record));
    }

    /**
     * 查询粉丝列表时，更新映射关系，判断userId是否有关注这个用户
     * todo 这种方式比较消耗性能，可以在粉丝列表中筛除userId关注的用户
     * @param followList
     * @param loginUserId
     */
    @Override
    public void updateUserFollowRelationId(PageListVo<FollowUserInfoDTO> followList, Long loginUserId) {
        if (loginUserId == null) {
            followList.getList().forEach(r -> {
                r.setRelationId(null);
                r.setFollowed(false);
            });
            return;
        }

        // 判断登录用户与给定的用户列表的关注关系
        Set<Long> userIds = followList.getList().stream().map(FollowUserInfoDTO::getUserId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }

        List<UserRelationDO> relationList = userRelationDao.listUserRelations(loginUserId, userIds);
        Map<Long, UserRelationDO> relationMap = MapUtils.toMap(relationList, UserRelationDO::getUserId, r -> r);
        followList.getList().forEach(follow -> {
            UserRelationDO relation = relationMap.get(follow.getUserId());
            if (relation == null) {
                follow.setRelationId(null);
                follow.setFollowed(false);
            } else if (Objects.equals(relation.getFollowState(), FollowStateEnum.FOLLOW.getCode())) {
                follow.setRelationId(relation.getId());
                follow.setFollowed(true);
            } else {
                follow.setRelationId(relation.getId());
                follow.setFollowed(false);
            }
        });
    }
}