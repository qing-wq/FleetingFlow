package ink.whi.video.repo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
@Data
@TableName("score")
public class Score {
    /**
     * 业务id
     */
    @TableId
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分数
     */
    private Float score;
}
