package ink.whi.common.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 * @author: qing
 * @Date: 2023/10/27
 */
@Getter
public enum FileTypeEnum {
    PUBLIC(0, "公开"),
    PRIVATE(1, "私有");

    private int code;
    private String desc;

    FileTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
