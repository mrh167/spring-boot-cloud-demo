package com.msc.fix.lisa.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.msc.fix.lisa.GeneratorConfig;
import com.msc.fix.lisa.mbp.NameConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/29
 * Time: 14:25
 * Description: No Description
 * http://localhost:8081
 */
@Configuration
public class GeneratorConfigruation {

    @Bean
    public GeneratorConfig generatorConfig(){
       return GeneratorConfig.builder()
                .jdbcUrl("jdbc:mysql://localhost:3306/vsc_portal?characterEncoding=utf-8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai")
                .userName("root")
                .password("root")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
                .schemaName(DbType.MYSQL.getDb())
                //如果需要修改各类生成文件的默认命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法：
                .nameConverter(new NameConverter() {
                    //自定义Service类文件的名称规则
                    @Override
                    public String serviceNameConvert(String tableName) {
                        return this.entityNameConvert(tableName) + "Service";
                    }
                })
                .basePackage("com.msc.fix.lisa.coding")
                .port(8068)
                .build();
    }
}
