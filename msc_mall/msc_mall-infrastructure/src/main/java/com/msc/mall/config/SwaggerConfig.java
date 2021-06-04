package com.msc.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/4
 * Time: 19:11
 * Description: No Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置Swagger信息
     */
    @Bean
    public Docket createRestApi() {
        /*==========添加head参数start================*/
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("Token令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.msc.mall.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);//需放置build之后
        ;
        return docket;
    }
    private ApiInfo apiInfo(){
        //作者信息
        return new ApiInfoBuilder()
                // 页面标题
                .title("RESTful APIs")
                .description("API接口文档")
                .termsOfServiceUrl("http://localhost:8018/doc.html")
                .contact(new Contact("作者","网址","邮箱"))
                .version("v1.0")
                .build();
    }
}
