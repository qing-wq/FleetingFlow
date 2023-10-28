package ink.whi.common.enums;

import lombok.Getter;

/**
 * 用户页面选择枚举
 *
 * @author qing
 * @date 2022/7/19
 */
@Getter
public enum HomeSelectEnum {

    WORKS("video", "作品"),
    READ("history", "浏览记录"),
    COLLECTION("collection", "收藏"),
    LIKE("like", "喜欢");

    HomeSelectEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static HomeSelectEnum fromCode(String code) {
        for (HomeSelectEnum value : HomeSelectEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return HomeSelectEnum.WORKS;
    }
}
