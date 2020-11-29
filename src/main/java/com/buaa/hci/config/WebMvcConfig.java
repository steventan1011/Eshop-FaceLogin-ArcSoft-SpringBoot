package com.buaa.hci.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author T-bk
 * @Date 2020/11/29 17:06
 * @Version 1.0
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    告知系统static 当成 静态资源访问

        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\faceImage\\";
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/faceImage/**").addResourceLocations("file:"+path);
    }
}
