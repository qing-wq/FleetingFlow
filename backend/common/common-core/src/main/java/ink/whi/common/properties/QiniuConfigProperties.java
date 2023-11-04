/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ink.whi.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 七牛云对象存储配置类
 *
 * @author qing
 * @date 2023/10/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu.config")
public class QiniuConfigProperties {

    /**
     * accessKey
     */
    public String accessKey;

    /**
     * secretKey
     */
    public String secretKey;

    /**
     * Bucket
     */
    public String bucket;

    /**
     * 地域
     */
    public String zone;

    /**
     * 外链域名
     */
    public String host;

    /**
     * 空间类型：公开/私有
     */
    public String type = "公开";

    public String buildUrl(String url) {
        if (!url.startsWith(host)) {
            return host + "/" + url;
        }
        return url;
    }
}
