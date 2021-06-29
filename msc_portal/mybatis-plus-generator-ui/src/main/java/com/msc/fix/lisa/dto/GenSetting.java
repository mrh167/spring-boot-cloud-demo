package com.msc.fix.lisa.dto;

import java.util.List;

/**
 * 定义生成代码时每次可能发生变化的一些配置项目
 */
public class GenSetting {

    /**
     * 需要生成的文件类型
     */
    private List<String> choosedOutputFiles;

    /**
     * 文件存在时是否覆盖
     */
    private boolean override;

    /**
     * 注释的作者
     */
    private String author;

    /**
     * 功能模块名
     */
    private String moduleName;

    /**
     * 临时模板参数，由用户自行输入的临时参数，用于控制生成文件的可选部分
     */
    private List<String> choosedControllerMethods;

    public List<String> getChoosedOutputFiles() {
        return choosedOutputFiles;
    }

    public void setChoosedOutputFiles(List<String> choosedOutputFiles) {
        this.choosedOutputFiles = choosedOutputFiles;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<String> getChoosedControllerMethods() {
        return choosedControllerMethods;
    }

    public void setChoosedControllerMethods(List<String> choosedControllerMethods) {
        this.choosedControllerMethods = choosedControllerMethods;
    }
}
