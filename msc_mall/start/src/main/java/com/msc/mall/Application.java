package com.msc.mall;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Starter
 *
 * COLA framework initialization is configured in {@link com.msc.mall.config.ColaConfig}
 *
 * @author Frank Zhang
 */

@SpringBootApplication(scanBasePackages = {"com.msc.mall","com.alibaba.cola"})
//@PropertySources(
//        value = {
//                @PropertySource(value = {"classpath:application-dev.properties"}, encoding = "utf-8")
////                @PropertySource(value = {"classpath:jdbc.properties"}, encoding = "utf-8"),
////                @PropertySource(value = {"classpath:jmq.properties"}, encoding = "utf-8")
//        }
//)
@MapperScan("com.msc.mall.repository")
@Slf4j
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

    /**
     * Application
     */
    public Application() {
        super();
        setRegisterErrorPageFilter(false);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.bannerMode(Banner.Mode.OFF);
        return application.sources(Application.class);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 启动方法
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.bannerMode(Banner.Mode.OFF);
        builder.run(args);
        log.info("启动主程序成功" );
    }
}
