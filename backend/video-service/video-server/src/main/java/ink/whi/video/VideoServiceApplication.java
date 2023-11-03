package ink.whi.video;

import ink.whi.video.hook.interceptor.AuthorizeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAsync
@EnableCaching
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ink.whi.user.client")
@MapperScan(value = "ink.whi.video.repo")
public class VideoServiceApplication implements WebMvcConfigurer {

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(VideoServiceApplication.class).run(args);
    }
}