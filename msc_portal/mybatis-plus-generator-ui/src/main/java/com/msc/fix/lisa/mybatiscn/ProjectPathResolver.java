package com.msc.fix.lisa.mybatiscn;

import com.msc.fix.lisa.common.ServiceException;
import com.msc.fix.lisa.mybatiscn.util.PathUtil;
import com.google.common.base.Strings;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static com.msc.fix.lisa.mybatiscn.dto.Constant.PACKAGE_JAVA_PREFIX;
import static com.msc.fix.lisa.mybatiscn.dto.Constant.PACKAGE_RESOURCES_PREFIX;

public class ProjectPathResolver {

	private String sourcePath;

	private String resourcePath;

	private String baseProjectPath;

	private String basePackage;

	private Pattern packagePattern = Pattern.compile("[a-zA-Z]+[0-9a-zA-Z_]*(\\.[a-zA-Z]+[0-9a-zA-Z_]*)*");

	public ProjectPathResolver(String basePackage) {
		this.basePackage = basePackage;
		String curentThreadPath = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
		curentThreadPath = getUTF8String(curentThreadPath);
		String[] paths = curentThreadPath.split("/");
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < paths.length; i++) {
			String path = paths[i];
			if (i < paths.length - 2) {
				temp.append(path);
				temp.append("/");
			}
		}
		baseProjectPath = temp.toString();
		sourcePath = new File(baseProjectPath + "src/main/java").toString();
		resourcePath = new File(baseProjectPath + "src/main/resources").toString();
	}

	/**
	 * 中文文件夹UTF8编码
	 *
	 * @param basePath
	 * @return
	 */
	private String getUTF8String(String basePath) {
		try {
			basePath = URLDecoder.decode(basePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return basePath;
	}

	/**
	 * 将文件输出的包名转换为绝对路径
	 */
	public String convertPackageToPath(String packageName) {
		if (Strings.isNullOrEmpty(packageName)) {
			throw new ServiceException("包名为空");
		}
		boolean isResourceFile = false;
		if (packageName.startsWith(PACKAGE_RESOURCES_PREFIX)) {
			packageName = packageName.replaceFirst(PACKAGE_RESOURCES_PREFIX, "");
			isResourceFile = true;
		} else if (packageName.startsWith(PACKAGE_JAVA_PREFIX)) {
			packageName = packageName.replaceFirst(PACKAGE_JAVA_PREFIX, "");
			isResourceFile = false;
		}
		if (!packagePattern.matcher(packageName).matches()) {
			throw new ServiceException("不是合法的包名：" + packageName);
		}
		String[] folders = packageName.split("\\.");
		String path = sourcePath;
		if (isResourceFile) {
			path = resourcePath;
		}
		for (String folder : folders) {
			path = path + File.separator + folder;
		}
		return path;
	}

	public String convertPathToPackage(String path) {
		if (path.startsWith(sourcePath)) {
			path = path.replace(sourcePath, "");
		} else if (path.startsWith(resourcePath)) {
			path = path.replace(resourcePath, "");
		} else {
			throw new ServiceException("无法将该路径转换为包名：" + path);
		}
		String packageStr = path.replace(File.separator, ".");
		if (packageStr.startsWith(".")) {
			packageStr = packageStr.substring(1, packageStr.length());
		}
		return packageStr;
	}

	public String resolveEntityPackage() {
		return PathUtil.joinPackage(basePackage, "entity");
	}

	public String resolveControllerPackage() {
		return PathUtil.joinPackage(basePackage, "controller");
	}

	public String resolveServicePackage() {
		return PathUtil.joinPackage(basePackage, "service");
	}

	public String resolveServiceImplPackage() {
		return PathUtil.joinPackage(basePackage, "service", "impl");
	}

	public String resolveMapperPackage() {
		return PathUtil.joinPackage(basePackage, "mapper");
	}

	public String resolveMapperXmlPackage() {
		return PACKAGE_RESOURCES_PREFIX + "mapper";
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getBaseProjectPath() {
		return baseProjectPath;
	}

	public void setBaseProjectPath(String baseProjectPath) {
		this.baseProjectPath = baseProjectPath;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public Pattern getPackagePattern() {
		return packagePattern;
	}

	public void setPackagePattern(Pattern packagePattern) {
		this.packagePattern = packagePattern;
	}
}
