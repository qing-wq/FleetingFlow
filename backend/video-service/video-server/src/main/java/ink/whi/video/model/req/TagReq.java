package ink.whi.video.model.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Tag请求参数
 *
 * @author qing
 * @date 2023/10/31
 */
@Data
public class TagReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -5388767717047688998L;
    /**
     * ID
     */
    private Long tagId;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 类目ID
     */
    private Long categoryId;
}
