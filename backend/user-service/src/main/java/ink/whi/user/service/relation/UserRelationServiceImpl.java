package ink.whi.user.service.relation;

import ink.whi.common.enums.FollowStateEnum;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.rabbitmq.UserMqConstants;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.repo.entity.UserRelationDO;
import ink.whi.user.service.UserRelationService;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.model.req.UserRelationReq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageListVo<FollowUserInfoDTO> getUserFollowList(Long userId, PageParam pageParam) {
        List<FollowUserInfoDTO> userRelationList = userRelationDao.listUserFollows(userId, pageParam);
        return PageListVo.newVo(userRelationList, pageParam.getPageSize());
    }

    @Override
    public PageListVo<FollowUserInfoDTO> getUserFansList(Long userId, PageParam pageParam) {
        List<FollowUserInfoDTO> userRelationList = userRelationDao.listUserFans(userId, pageParam);
        return PageListVo.newVo(userRelationList, pageParam.getPageSize());
    }


    @Override
    public void saveUserRelation(UserRelationReq req) {
        UserRelationDO record = userRelationDao.getUserRelationByUserId(req.getUserId(), req.getFollowUserId());
        FollowStateEnum state = req.getFollowed() ? FollowStateEnum.FOLLOW : FollowStateEnum.CANCEL_FOLLOW;
        if (record == null) {
            record = new UserRelationDO().setUserId(req.getUserId()).setFollowUserId(req.getFollowUserId()).setFollowState(state.getCode());
            userRelationDao.save(record);
            // 发布消息
            rabbitTemplate.convertAndSend(UserMqConstants.USER_TOPIC_EXCHANGE, UserMqConstants.USER_FOLLOW_KEY, record);
            return;
        }

        record.setFollowState(state.getCode());
        userRelationDao.updateById(record);
        rabbitTemplate.convertAndSend(UserMqConstants.USER_TOPIC_EXCHANGE, UserMqConstants.USER_CANCEL_FOLLOW_KEY, record);
    }
}
