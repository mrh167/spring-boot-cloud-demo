import com.baomidou.mybatisplus.annotation.DbType;
import com.msc.fix.lisa.GeneratorConfig;
import com.msc.fix.lisa.MybatisPlusToolsApplication;
import com.msc.fix.lisa.mbp.NameConverter;

/**
 * mybatis-plus代码自动生成可视化ui，访问：http://localhost:8068
 */
public class TestApplication {

	public static void main(String[] args) {
		GeneratorConfig config = GeneratorConfig.builder()
				.jdbcUrl("jdbc:mysql://localhost/vsc_portal?characterEncoding=utf-8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai")
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
				.basePackage("com.yanyeori.mybatisplus.generatorui.example")
				.port(8068)
				.build();
		MybatisPlusToolsApplication.run(config);
	}
}
