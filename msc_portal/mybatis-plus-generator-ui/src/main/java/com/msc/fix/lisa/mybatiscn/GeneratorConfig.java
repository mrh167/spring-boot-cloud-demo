package com.msc.fix.lisa.mybatiscn;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.msc.fix.lisa.mybatiscn.mbp.NameConverter;
import com.msc.fix.lisa.mybatiscn.mbp.TemplateVaribleInjecter;

public class GeneratorConfig {

	/**
	 * 服务启动的端口号
	 */
	private Integer port = 8068;

	/**
	 * 生成的文件所保存的包路径
	 */
	private String basePackage = "generatorui.default";

	/**
	 * 数据库地址
	 */
	private String jdbcUrl;

	/**
	 * 数据库schema,POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
	 */
	private String schemaName;

	/**
	 * 数据库用户名
	 */
	private String userName;

	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 数据库驱动名
	 */
	private String driverClassName = "com.mysql.cj.jdbc.Driver";

	/**
	 * PostgreSQL的schema name
	 */
	private String postgreSQLschema;

	/**
	 * 数据库时间类型与java class的对应策略
	 */
	private DateType dateType;

	/**
	 * 开启 ActiveRecord 模式
	 */
	private boolean activeRecord = false;

	/**
	 * 注入自定义模板参数
	 */
	private TemplateVaribleInjecter templateVaribleInjecter;


	/**
	 * 自定义名称转换规则
	 */
	private NameConverter nameConverter;

	public NameConverter getAvailableNameConverter() {
		if (nameConverter == null) {
			nameConverter = new NameConverter() {
			};
		}
		return nameConverter;
	}

	public DateType getDateType() {
		if (this.dateType == null) {
			return DateType.ONLY_DATE;
		}
		return dateType;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getPostgreSQLschema() {
		return postgreSQLschema;
	}

	public void setPostgreSQLschema(String postgreSQLschema) {
		this.postgreSQLschema = postgreSQLschema;
	}

	public void setDateType(DateType dateType) {
		this.dateType = dateType;
	}

	public boolean isActiveRecord() {
		return activeRecord;
	}

	public void setActiveRecord(boolean activeRecord) {
		this.activeRecord = activeRecord;
	}

	public TemplateVaribleInjecter getTemplateVaribleInjecter() {
		return templateVaribleInjecter;
	}

	public void setTemplateVaribleInjecter(TemplateVaribleInjecter templateVaribleInjecter) {
		this.templateVaribleInjecter = templateVaribleInjecter;
	}

	public NameConverter getNameConverter() {
		return nameConverter;
	}

	public void setNameConverter(NameConverter nameConverter) {
		this.nameConverter = nameConverter;
	}

	public static GeneratorConfig builder() {
		return new GeneratorConfig();
	}

	public GeneratorConfig port(Integer port) {
		this.port = port;
		return this;
	}

	public GeneratorConfig basePackage(String basePackage) {
		this.basePackage = basePackage;
		return this;
	}

	public GeneratorConfig jdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
		return this;
	}

	public GeneratorConfig schemaName(String schemaName) {
		this.schemaName = schemaName;
		return this;
	}

	public GeneratorConfig userName(String userName) {
		this.userName = userName;
		return this;
	}

	public GeneratorConfig password(String password) {
		this.password = password;
		return this;
	}

	public GeneratorConfig driverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
		return this;
	}

	public GeneratorConfig postgreSQLschema(String postgreSQLschema) {
		this.postgreSQLschema = postgreSQLschema;
		return this;
	}

	public GeneratorConfig dateType(DateType dateType) {
		this.dateType = dateType;
		return this;
	}

	public GeneratorConfig activeRecord(boolean activeRecord) {
		this.activeRecord = activeRecord;
		return this;
	}

	public GeneratorConfig templateVaribleInjecter(TemplateVaribleInjecter templateVaribleInjecter) {
		this.templateVaribleInjecter = templateVaribleInjecter;
		return this;
	}

	public GeneratorConfig nameConverter(NameConverter nameConverter) {
		this.nameConverter = nameConverter;
		return this;
	}

	public GeneratorConfig build() {
		return this;
	}
}
