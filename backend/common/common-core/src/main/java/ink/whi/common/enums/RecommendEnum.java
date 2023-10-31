package ink.whi.common.enums;

import lombok.Getter;

/**
 * 加精状态枚举
 *
 * @author qing
 * @date 2022/7/19
 */
@Getter
public enum RecommendEnum {

    NOT_RECOMMEND(0, "不推荐"),
    RECOMMEND(1, "推荐");

    RecommendEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static RecommendEnum formCode(Integer code) {
        for (RecommendEnum value : RecommendEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return RecommendEnum.NOT_RECOMMEND;
    }
}
