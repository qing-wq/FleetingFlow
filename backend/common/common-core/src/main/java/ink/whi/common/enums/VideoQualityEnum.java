package ink.whi.common.enums;

import lombok.Getter;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Getter
public enum VideoQualityEnum {

    standard(0, "标清"),
    fluency(1, "流畅"),
    Super(2, "超清");

    private int code;
    private String desc;

    VideoQualityEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
