package ink.whi.comment;

import ink.whi.web.hook.interceptor.AuthorizeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
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
@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"ink.whi.video.client", "ink.whi.user.client"})
@LoadBalancerClients({
        @LoadBalancerClient("user-service"),
        @LoadBalancerClient("video-service")
})
@MapperScan(value = "ink.whi.comment.repo")
public class CommentApplication implements WebMvcConfigurer {

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(CommentApplication.class).allowCircularReferences(true).run(args);
    }
}