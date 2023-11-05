package ink.whi.user.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/11/5
 */
@Data
public class EmailVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -8560585303684975482L;

    private String to;

    private String title = "欢迎注册FleetingFlow";

    private String content = "";
}
