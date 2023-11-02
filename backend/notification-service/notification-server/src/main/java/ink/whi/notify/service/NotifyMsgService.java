package ink.whi.notify.service;


import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.NotifyMsgDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.dto.UserRelationDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public interface NotifyMsgService {
    int queryUserNotifyMsgCount(Long userId);

    Map<String, Integer> queryUnreadCounts(Long userId);

    PageListVo<NotifyMsgDTO> queryUserNotices(Long loginUserId, NotifyTypeEnum typeEnum, PageParam newPageInstance);

    void saveCommentNotify(CommentDTO comment);

    void saveReplyNotify(CommentDTO comment);

    void saveVideoPraise(UserFootDTO foot);

    void saveVideoCollect(UserFootDTO foot);

    void removeVideoPraise(UserFootDTO foot);

    void removeVideoCollect(UserFootDTO foot);

    void saveFollowNotify(UserRelationDTO relation);

    void removeFollowNotify(UserRelationDTO relation);

    void saveRegisterSystemNotify(Long userId);
}
