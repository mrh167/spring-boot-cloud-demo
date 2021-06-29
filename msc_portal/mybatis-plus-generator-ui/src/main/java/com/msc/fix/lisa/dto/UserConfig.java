package com.msc.fix.lisa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.msc.fix.lisa.strategy.*;

import java.util.List;

import static com.msc.fix.lisa.dto.Constant.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConfig {

    private List<OutputFileInfo> outputFiles;

    private EntityStrategy entityStrategy = new EntityStrategy();

    private MapperStrategy mapperStrategy = new MapperStrategy();

    private MapperXmlStrategy mapperXmlStrategy = new MapperXmlStrategy();

    private ControllerStrategy controllerStrategy = new ControllerStrategy();

    private ServiceStrategy serviceStrategy = new ServiceStrategy();

    private ServiceImplStrategy serviceImplStrategy = new ServiceImplStrategy();

    @JsonIgnore
    public OutputFileInfo getControllerInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_CONTROLLER.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getEntityInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_ENTITY.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getMapperInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_MAPPER.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getMapperXmlInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_MAPPER_XML.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getServiceInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_SERVICE.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getServiceImplInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> FILE_TYPE_SERVICEIMPL.equals(f.getFileType()))).findFirst().get();
    }

    public List<OutputFileInfo> getOutputFiles() {
        return outputFiles;
    }

    public void setOutputFiles(List<OutputFileInfo> outputFiles) {
        this.outputFiles = outputFiles;
    }

    public EntityStrategy getEntityStrategy() {
        return entityStrategy;
    }

    public void setEntityStrategy(EntityStrategy entityStrategy) {
        this.entityStrategy = entityStrategy;
    }

    public MapperStrategy getMapperStrategy() {
        return mapperStrategy;
    }

    public void setMapperStrategy(MapperStrategy mapperStrategy) {
        this.mapperStrategy = mapperStrategy;
    }

    public MapperXmlStrategy getMapperXmlStrategy() {
        return mapperXmlStrategy;
    }

    public void setMapperXmlStrategy(MapperXmlStrategy mapperXmlStrategy) {
        this.mapperXmlStrategy = mapperXmlStrategy;
    }

    public ControllerStrategy getControllerStrategy() {
        return controllerStrategy;
    }

    public void setControllerStrategy(ControllerStrategy controllerStrategy) {
        this.controllerStrategy = controllerStrategy;
    }

    public ServiceStrategy getServiceStrategy() {
        return serviceStrategy;
    }

    public void setServiceStrategy(ServiceStrategy serviceStrategy) {
        this.serviceStrategy = serviceStrategy;
    }

    public ServiceImplStrategy getServiceImplStrategy() {
        return serviceImplStrategy;
    }

    public void setServiceImplStrategy(ServiceImplStrategy serviceImplStrategy) {
        this.serviceImplStrategy = serviceImplStrategy;
    }
}
