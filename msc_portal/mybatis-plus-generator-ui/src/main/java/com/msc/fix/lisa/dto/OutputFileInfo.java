package com.msc.fix.lisa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputFileInfo {

    private String fileType;

    /**
     * 文件的输出包名
     */
    private String outputLocation;

    private String templateName;

    private String templatePath;

    private boolean builtIn;

    public String getOutputPackage() {
        if (Strings.isNullOrEmpty(outputLocation)) {
            return "";
        }
        if (outputLocation.startsWith(Constant.PACKAGE_RESOURCES_PREFIX)) {
            return outputLocation.replaceFirst(Constant.PACKAGE_RESOURCES_PREFIX, "");
        } else if (outputLocation.startsWith(Constant.PACKAGE_JAVA_PREFIX)) {
            return outputLocation.replaceFirst(Constant.PACKAGE_JAVA_PREFIX, "");
        }
        return outputLocation;
    }

    @JsonIgnore
    public String getAvailableTemplatePath() {
        if (!Strings.isNullOrEmpty(templatePath)) {
            return templatePath;
        }
        if (fileType.equals(Constant.FILE_TYPE_ENTITY)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/entity.java.btl";
        } else if (fileType.equals(Constant.FILE_TYPE_MAPPER)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/mapper.java.btl";
        } else if (fileType.equals(Constant.FILE_TYPE_MAPPER_XML)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/mapper.xml.btl";
        } else if (fileType.equals(Constant.FILE_TYPE_SERVICE)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/service.java.btl";
        } else if (fileType.equals(Constant.FILE_TYPE_SERVICEIMPL)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/serviceImpl.java.btl";
        } else if (fileType.equals(Constant.FILE_TYPE_CONTROLLER)) {
            return Constant.RESOURCE_PREFIX_CLASSPATH + "codetpls/controller.java.btl";
        }
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OutputFileInfo) {
            OutputFileInfo file = (OutputFileInfo) obj;
            if (file.getFileType() == null || this.getFileType() == null) {
                return false;
            }
            return file.getFileType().equalsIgnoreCase(this.getFileType());
        }
        return false;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOutputLocation() {
        return outputLocation;
    }

    public void setOutputLocation(String outputLocation) {
        this.outputLocation = outputLocation;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }
}
