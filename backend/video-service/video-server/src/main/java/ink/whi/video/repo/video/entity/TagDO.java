package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tag")
public class TagDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 3796460143933607644L;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 是否删除
     */
    private Integer deleted;
}
