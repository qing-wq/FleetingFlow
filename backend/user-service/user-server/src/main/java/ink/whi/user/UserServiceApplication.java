package ink.whi.user;

import ink.whi.web.hook.interceptor.AuthorizeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@EnableAsync
@EnableCaching
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"ink.whi.video.client", "ink.whi.comment.client"})
@LoadBalancerClients({
        @LoadBalancerClient("video-service"),
        @LoadBalancerClient("comment-service")
})
@MapperScan(value = {"ink.whi.user.repo", "ink.whi.user.notify.repo"})
public class UserServiceApplication implements WebMvcConfigurer {

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserServiceApplication.class).allowCircularReferences(true).run(args);
    }
}