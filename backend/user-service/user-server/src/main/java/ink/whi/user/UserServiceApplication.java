package ink.whi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@EnableAsync
@EnableCaching
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "ink.whi.user.repo")
public class UserServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UserServiceApplication.class).allowCircularReferences(true).run(args);
    }
}