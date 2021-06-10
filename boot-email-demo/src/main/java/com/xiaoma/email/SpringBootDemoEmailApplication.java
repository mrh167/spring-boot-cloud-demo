package com.xiaoma.email;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.xiaoma.email.emaildemo.mapper")
public class SpringBootDemoEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoEmailApplication.class, args);
    }
}
