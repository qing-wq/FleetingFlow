import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class MyTest {
    @Test
    public void test() {
        String plainPwd = "12salt-key3456";
        System.out.println(DigestUtils.md5DigestAsHex(plainPwd.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void test1() throws NoSuchAlgorithmException {
        String key = "file name";
        String file = "file name";
        String aaa = "djaskfdhjkafhjdkafhdjskhsjkdjsl";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        System.out.println(key.hashCode());
        System.out.println(file.hashCode());
        System.out.println(calculateHash(key));
        System.out.println(calculateHash(aaa));
    }

    public static String calculateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
