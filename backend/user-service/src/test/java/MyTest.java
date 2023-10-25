import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MyTest extends BasicTest{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String plainPwd = "123456";
        System.out.println(passwordEncoder.encode(plainPwd));
    }
}
