package ink.whi.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 视频操作枚举
 *
 * @author qing
 * @date 2022/9/3
 */
@Getter
public enum VideoEventEnum {
    CREATE(1, "创建"),
    ONLINE(2, "发布"),
    REVIEW(3, "审核"),
    DELETE(4, "删除"),
    OFFLINE(5, "下线"),
    ;


    private int type;
    private String msg;

    private static Map<Integer, VideoEventEnum> mapper;

    static {
        mapper = new HashMap<>();
        for (VideoEventEnum type : values()) {
            mapper.put(type.type, type);
        }
    }

    VideoEventEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static VideoEventEnum typeOf(int type) {
        return mapper.get(type);
    }

    public static VideoEventEnum typeOf(String type) {
        return valueOf(type.toUpperCase().trim());
    }
}
