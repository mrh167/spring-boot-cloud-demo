/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*@Configuration
public class GuliCorsConfigruation {
*//*
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfigruation corsConfigruation = new CorsConfigruation();
        //1.配置跨域  Access-Control-Allow-Origin
        corsConfigruation.addAllowedHeader("*");
        corsConfigruation.addAllowedMethod("*");
        corsConfigruation.addAllowedOrigin("*");
        corsConfigruation.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfigruation);
        return new CorsWebFilter(source);
    }*//*
}*/
@Configuration
public class GuliCorsConfigruation implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }
}
