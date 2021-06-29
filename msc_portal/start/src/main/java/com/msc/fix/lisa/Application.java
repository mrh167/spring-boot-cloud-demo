package com.msc.fix.lisa;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.msc.fix.lisa.mybatiscn.GeneratorConfig;
import com.msc.fix.lisa.mybatiscn.configurer.GeneratorConfigruation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.*;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * Spring Boot Starter
 *
 * COLA framework initialization is configured in {@link com.msc.fix.lisa.config.ColaConfig}
 *
 * @author Frank Zhang
 */
@Import({
        DispatcherServletAutoConfiguration.class,
        ServletWebServerFactoryAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        WebMvcAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = {"com.msc.fix.lisa","com.alibaba.cola"})
@MapperScan("com.msc.fix.lisa.repository")
public class Application {

    private static GeneratorConfig generatorConfig;


    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfigruation.generatorConfig();
        Application.run(config);
    }

    public static void run(GeneratorConfig generatorConfigs){
        if (Strings.isNullOrEmpty(generatorConfigs.getJdbcUrl())) {
            throw new IllegalArgumentException("jdbcUrl必须要设置");
        }
        Application.generatorConfig = generatorConfigs;
        Map<String, Object> props = Maps.newHashMap();
        props.put("spring.resources.static-locations", "classpath:/generator-ui/");
        new SpringApplicationBuilder()
                .properties(props)
                .sources(Application.class)
                .run(new String[0]);
    }
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerConfig(GeneratorConfig config) {
        return factory -> {
            if (config.getPort() != null) {
                factory.setPort(Application.generatorConfig.getPort());
            } else {
                factory.setPort(8068);
            }
            factory.setContextPath("");
        };
    }

//    @Bean
//    public GeneratorConfig generatorConfig() {
//        return Application.generatorConfig;
//    }

}
