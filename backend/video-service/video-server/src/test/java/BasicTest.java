import ink.whi.video.VideoServiceApplication;
import ink.whi.video.utils.AIUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@SpringBootTest(classes = VideoServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {

    @Test
    public void Test() throws JSONException {
        List<String> tags = AIUtil.getTagRecommendResults("猫猫可爱");
        System.out.println(tags);
    }
}
