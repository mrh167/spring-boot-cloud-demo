package org.es.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/8
 * Time: 16:23
 * Description: No Description
 */
@SpringBootApplication(exclude = RestClientAutoConfiguration.class)
public class EsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class);
    }
}
