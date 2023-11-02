package ink.whi.notify.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.common.model.dto.NotifyMsgDTO;
import ink.whi.common.model.page.PageParam;
import ink.whi.notify.repo.entity.NotifyMsgDO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public interface NotifyMsgMapper extends BaseMapper<NotifyMsgDO> {
    /**
     * 视频相关的消息
     * @param userId
     * @param type
     * @param pageParam
     * @return
     */
    List<NotifyMsgDTO> listArticleRelatedNotices(Long userId, Integer type, PageParam pageParam);

    /**
     * 普通消息，如关注等
     * @param userId
     * @param type
     * @param pageParam
     * @return
     */
    List<NotifyMsgDTO> listNormalNotices(Long userId, Integer type, PageParam pageParam);
}
