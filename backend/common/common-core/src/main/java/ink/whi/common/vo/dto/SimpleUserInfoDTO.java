package ink.whi.common.vo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Data
public class SimpleUserInfoDTO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 作者头像
     */
    private String picture;

    /**
     * 作者名
     */
    private String userName;
}
