package com.zhitail.app.config;

import io.swagger.annotations.ApiOperation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
  * swagger配置类
  * @ClassName: SwaggerConfig
  * @Description: TODO
  * @author huangshun
  * @date 2017年11月20日 下午1:35:25
  *
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "zyframework", name = "swagger-open", havingValue = "true")
public class SwaggerConfig{

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))                         //这里采用包含注解的方式来确定要显示的接口
                //.apis(RequestHandlerSelectors.basePackage("com.stylefeng.guns.modular.system.controller"))    //这里采用包扫描的方式来确定要显示的接口
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("华西康复系统接口")
                .description("Api文档")
                .termsOfServiceUrl("http://www.cdzhiyong.com")
                .contact("华西康复项目组黄顺")
                .version("v1.0")
                .build();
    }

}
