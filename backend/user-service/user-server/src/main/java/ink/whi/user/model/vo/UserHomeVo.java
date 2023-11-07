package ink.whi.user.model.vo;

import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.user.model.dto.FollowUserInfoDTO;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.video.dto.VideoInfoDTO;
import lombok.Data;

/**
 * @author qing
 * @date 2023/10/24
 */
@Data
public class UserHomeVo {
    /**
     * 用户主页选择标签
     */
    private String homeSelectType;

    /**
     * 视频列表
     */
    private PageListVo<VideoInfoDTO> homeSelectList;

    /**
     * 用户个人信息
     */
    private UserStatisticInfoDTO userHome;
}
