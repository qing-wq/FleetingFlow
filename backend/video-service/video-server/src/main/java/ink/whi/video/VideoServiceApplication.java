package ink.whi.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ink.whi.user.client")
@MapperScan(value = "ink.whi.video.repo")
public class VideoServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(VideoServiceApplication.class).run(args);
    }
}