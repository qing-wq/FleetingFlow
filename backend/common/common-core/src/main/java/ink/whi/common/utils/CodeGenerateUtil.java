package ink.whi.common.utils;

import java.util.Random;

/**
 * @author: qing
 * @Date: 2023/11/5
 */
public class CodeGenerateUtil {

    /**
     * 生成四位随机验证码
     * @return
     */
    public static String genCode() {
        Random random = new Random();
        return String.valueOf(random.nextLong(9000) + 1000);
    }
}
