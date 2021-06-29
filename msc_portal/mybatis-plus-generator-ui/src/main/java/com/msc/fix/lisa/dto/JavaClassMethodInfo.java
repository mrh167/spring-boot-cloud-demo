package com.msc.fix.lisa.dto;

import java.util.List;
import java.util.Set;

public class JavaClassMethodInfo {

	private String classRef;

	private String methodName;

	private String returnType;

	private String comments;

	private List<DtoFieldInfo> params;

	private Set<String> importJavaTypes;

	public String getClassRef() {
		return classRef;
	}

	public void setClassRef(String classRef) {
		this.classRef = classRef;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<DtoFieldInfo> getParams() {
		return params;
	}

	public void setParams(List<DtoFieldInfo> params) {
		this.params = params;
	}

	public Set<String> getImportJavaTypes() {
		return importJavaTypes;
	}

	public void setImportJavaTypes(Set<String> importJavaTypes) {
		this.importJavaTypes = importJavaTypes;
	}

	public JavaClassMethodInfo classRef(String classRef) {
		this.classRef = classRef;
		return this;
	}

	public JavaClassMethodInfo methodName(String methodName) {
		this.methodName = methodName;
		return this;
	}

	public JavaClassMethodInfo returnType(String returnType) {
		this.returnType = returnType;
		return this;
	}

	public JavaClassMethodInfo comments(String comments) {
		this.comments = comments;
		return this;
	}

	public JavaClassMethodInfo params(List<DtoFieldInfo> params) {
		this.params = params;
		return this;
	}

	public JavaClassMethodInfo importJavaTypes(Set<String> importJavaTypes) {
		this.importJavaTypes = importJavaTypes;
		return this;
	}

	public static JavaClassMethodInfo builder() {
		return new JavaClassMethodInfo();
	}

	public JavaClassMethodInfo build() {
		return this;
	}
}
