package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("read_count")
public class ReadCountDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 4992415891127946197L;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 计数
     */
    private Integer cnt;

}
