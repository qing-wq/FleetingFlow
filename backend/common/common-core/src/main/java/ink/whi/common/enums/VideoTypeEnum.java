package ink.whi.common.enums;

import lombok.Getter;

@Getter
public enum VideoTypeEnum {

    EMPTY(0, ""),
    ARTICLE(1, "视频"),
    COMMENT(2, "评论");

    VideoTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static VideoTypeEnum formCode(Integer code) {
        for (VideoTypeEnum value : VideoTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return VideoTypeEnum.EMPTY;
    }

}
