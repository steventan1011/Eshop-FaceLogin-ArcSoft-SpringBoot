package com.buaa.hci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author T-bk
 * @Date 2020/10/13 13:31
 * @Version 2.0
 */

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
//http://localhost:8080/swagger-ui.html#/
//http://localhost:8080/docs.html
public class SwaggerConfig {
    //配置了Swagger 的Docket的bean实例
    @Bean
    public Docket docket1(Environment environment) {
        // 设置要显示swagger的环境
//        Profiles of = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
//        boolean b = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 分组
                .groupName("T-bk")
//                .enable(b) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.buaa.hci.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
//                .paths(PathSelectors.ant("/kuang/**"))
                .build();
    }


//    @Bean
//    public Docket docket2() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("123测试");
//    }

    //配置Swagger信息=apiInfo
    public ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("T-bk","https://www.baidu.com","steventan1011@sina.com");
        return new ApiInfo("T-bk的SwaggerAPI文档",
                "作者信息暂无",
                "v1.0",
                "urn:tos",
                contact, "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
