package ink.whi.user.notify.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.model.dto.NotifyMsgDTO;
import ink.whi.user.notify.repo.entity.NotifyMsgDO;
import org.springframework.stereotype.Component;

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
