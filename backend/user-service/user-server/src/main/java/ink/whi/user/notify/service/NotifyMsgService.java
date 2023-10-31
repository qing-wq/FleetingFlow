package ink.whi.user.notify.service;


import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.model.dto.NotifyMsgDTO;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.repo.entity.UserRelationDO;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public interface NotifyMsgService {
    int queryUserNotifyMsgCount(Long userId);

    Map<String, Integer> queryUnreadCounts(Long userId);

    PageListVo<NotifyMsgDTO> queryUserNotices(Long loginUserId, NotifyTypeEnum typeEnum, PageParam newPageInstance);

//    void saveCommentNotify(CommentDO comment);

//    void saveReplyNotify(CommentDO comment);

    void saveArticlePraise(UserFootDO foot);

    void saveArticleCollect(UserFootDO foot);

    void removeArticlePraise(UserFootDO foot);

    void removeArticleCollect(UserFootDO foot);

    void saveFollowNotify(UserRelationDO relation);

    void removeFollowNotify(UserRelationDO relation);

    void saveRegisterSystemNotify(Long userId);
}
