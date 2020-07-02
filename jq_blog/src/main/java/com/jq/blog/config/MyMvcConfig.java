package com.jq.blog.config;

import com.jq.blog.compoment.LoginHandlerInterceptor;
import com.jq.blog.compoment.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by user on 2020/4/27.
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer{

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer=new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/blog.html").setViewName("blog");
//                registry.addViewController("/post.html").setViewName("post");
                registry.addViewController("/publish.html").setViewName("publish");
                registry.addViewController("/publish").setViewName("publish");
                registry.addViewController("/main").setViewName("bms/main");
                registry.addViewController("/main.html").setViewName("bms/main");
                registry.addViewController("/loding").setViewName("bms/loding");
                registry.addViewController("/error.html").setViewName("bms/error");
                registry.addViewController("/unsubscribe.html").setViewName("unsubscribe");
                registry.addViewController("/contact.html").setViewName("contact");
            }


        };

        return  configurer;
    }

    @Bean
    public LocaleResolver localeResolver(){
        return  new MyLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/main","/main.html","/publish","/publish.html").excludePathPatterns("/error.html");

    }
}
