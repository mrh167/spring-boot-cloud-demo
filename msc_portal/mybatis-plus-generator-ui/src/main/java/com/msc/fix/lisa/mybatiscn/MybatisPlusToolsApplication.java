//package com.msc.fix.lisa.mybatiscn;
//
//import com.google.common.base.Strings;
//import com.google.common.collect.Maps;
//import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.servlet.*;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Import;
//
//import java.util.Map;
//
///**
// * 不使用@SpringBootApplication和@EnableAutoConfiguration
// * 注解，避免启动时被宿主系统的自动配置所干扰，直接注入需要的配置类
// */
//
//@Import({
//		DispatcherServletAutoConfiguration.class,
//		ServletWebServerFactoryAutoConfiguration.class,
//		HttpEncodingAutoConfiguration.class,
//		HttpMessageConvertersAutoConfiguration.class,
//		MultipartAutoConfiguration.class,
//		ErrorMvcAutoConfiguration.class,
//		WebMvcAutoConfiguration.class})
//@ComponentScan("com.msc.fix.lisa")
//public class MybatisPlusToolsApplication {
//
//	private static GeneratorConfig generatorConfig;
//
//	public static void run(GeneratorConfig generatorConfig) {
//		if (Strings.isNullOrEmpty(generatorConfig.getJdbcUrl())) {
//			throw new IllegalArgumentException("jdbcUrl必须要设置");
//		}
//		MybatisPlusToolsApplication.generatorConfig = generatorConfig;
//		Map<String, Object> props = Maps.newHashMap();
//		props.put("spring.resources.static-locations", "classpath:/generator-ui/");
//		new SpringApplicationBuilder()
//				.properties(props)
//				.sources(MybatisPlusToolsApplication.class)
//				.run(new String[0]);
//	}
//
//
//}
