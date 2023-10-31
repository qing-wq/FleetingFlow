package ink.whi.common.context;

import ink.whi.common.vo.dto.BaseUserDTO;
import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public class ReqInfoContext {

    /**
     * 本地线程变量
     */
    private static ThreadLocal<ReqInfo> contexts = new ThreadLocal<>();

    public static void addReqInfo(ReqInfo reqInfo) {
        contexts.set(reqInfo);
    }

    public static void clear() {
        contexts.remove();
    }

    public static ReqInfo getReqInfo() {
        ReqInfo reqInfo = contexts.get();
            return reqInfo;
    }

    @Data
    public static class ReqInfo {
        /**
         * 访问的域名
         */
        private String host;
        /**
         * 访问路径
         */
        private String path;
        /**
         * referer
         */
        private String referer;
        /**
         * post 表单参数
         */
        private String payload;
        /**
         * 设备信息
         */
        private String userAgent;

        /**
         * 用户id
         */
        private Long userId;
        /**
         * 用户信息
         */
        private BaseUserDTO user;
        /**
         * 消息数量
         */
        private Integer msgNum;
    }
}