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

    VIDEO("video", "视频"),
    READ("read", "浏览记录"),
    LIKE("follow", "关注"),
    COLLECTION("collection", "收藏");

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
        return HomeSelectEnum.VIDEO;
    }
}
