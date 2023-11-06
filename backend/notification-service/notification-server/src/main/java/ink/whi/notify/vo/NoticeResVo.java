package ink.whi.notify.vo;

import ink.whi.common.model.dto.NotifyMsgDTO;
import ink.whi.common.model.page.PageListVo;
import lombok.Data;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/11/6
 */
@Data
public class NoticeResVo {
    /**
     * 消息通知列表
     */
    private PageListVo<NotifyMsgDTO> list;

    /**
     * 每个分类的未读数量
     */
    private Map<String, Integer> unreadCountMap;

    /**
     * 当前选中的消息类型
     */
    private String selectType;
}
