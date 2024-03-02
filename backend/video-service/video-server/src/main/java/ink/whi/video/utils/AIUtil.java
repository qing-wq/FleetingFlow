package ink.whi.video.utils;

import ink.whi.common.utils.SpringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Component
public class AIUtil {

    public static List<String> getTagRecommendResults(String str) throws JSONException {
        List<String> result = new ArrayList<>(15);
        String data = getAICallResp(List.of(str));
        result.addAll(Arrays.asList(data.split("%%")));
        return result;
    }


    public static String getCategoryByTitle(String str) throws JSONException {
        return getAICallResp(List.of(str));
    }

    public static List<Long> getVideoRecommendResults(Long userId, Long categoryId) throws JSONException {
        List<Long> result = new ArrayList<>(15);
        String data = getAICallResp(List.of(userId, categoryId));
        Arrays.stream(data.split(",")).map(Long::parseLong).forEach(result::add);
        return result;
    }

    public static <T> String getAICallResp(List<T> param) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", param);
        // 创建请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送HTTP请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(SpringUtil.getConfig("ai.config.tag"), HttpMethod.POST, requestEntity, String.class);

        // 获取响应
        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        return  (String) responseJson.getJSONArray("data").get(0);
    }
}
