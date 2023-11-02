package ink.whi.common.model.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1314162575898615006L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
