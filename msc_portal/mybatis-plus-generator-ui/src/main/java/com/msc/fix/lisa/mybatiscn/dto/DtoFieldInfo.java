package com.msc.fix.lisa.mybatiscn.dto;

import cn.hutool.core.util.StrUtil;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import java.util.Set;

public class DtoFieldInfo {

    private Set<String> importJavaTypes = Sets.newHashSet();

    private String columnName;

    private String shortJavaType;

    private String propertyName;

    private NodeList<AnnotationExpr> annotations;

    public String getGetMethodName() {
        String prefix = "get";
        if ("boolean".equalsIgnoreCase(shortJavaType)) {
            prefix = "is";
        }
        return prefix + StrUtil.upperFirst(propertyName);
    }

    public String getSetMethodName() {
        return "set" + StrUtil.upperFirst(propertyName);
    }

    public Boolean isConverted() {
        return columnName.equals(propertyName);
    }

    public void addImportJavaType(String type) {
        if (!Strings.isNullOrEmpty(type)) {
            importJavaTypes.add(type);
        }
    }

    public Set<String> getImportJavaTypes() {
        return importJavaTypes;
    }

    public void setImportJavaTypes(Set<String> importJavaTypes) {
        this.importJavaTypes = importJavaTypes;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getShortJavaType() {
        return shortJavaType;
    }

    public void setShortJavaType(String shortJavaType) {
        this.shortJavaType = shortJavaType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public NodeList<AnnotationExpr> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(NodeList<AnnotationExpr> annotations) {
        this.annotations = annotations;
    }
}
