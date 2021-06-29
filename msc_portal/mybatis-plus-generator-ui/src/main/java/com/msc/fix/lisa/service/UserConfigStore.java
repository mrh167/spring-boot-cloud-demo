package com.msc.fix.lisa.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.msc.fix.lisa.GeneratorConfig;
import com.msc.fix.lisa.common.ServiceException;
import com.msc.fix.lisa.dto.UserConfig;
import com.msc.fix.lisa.util.JsonUtil;
import com.msc.fix.lisa.util.PathUtil;
import com.google.common.io.Files;
import com.msc.fix.lisa.dto.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

@Component
public class UserConfigStore {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String storeDir;

    private String userConfigPath;

    @Autowired
    private OutputFileInfoService outputFileInfoService;

    @Autowired
    private GeneratorConfig generatorConfig;

    @PostConstruct
    public void init() {
        this.storeDir = PathUtil.joinPath(System.getProperty("user.home"), Constant.CONFIG_HOME, generatorConfig.getBasePackage());
        this.userConfigPath = this.storeDir + File.separator + "user-config.json";
    }

    public String getTemplateStoreDir() {
        return PathUtil.joinPath(this.storeDir, Constant.TEMPLATE_STORE_DIR);
    }

    public UserConfig getDefaultUserConfig() {
        UserConfig userConfig = getUserConfigFromFile();
        if (userConfig == null) {
            userConfig = new UserConfig();
            userConfig.setOutputFiles(outputFileInfoService.getBuiltInFileInfo());
        }
        return userConfig;
    }

    public UserConfig getUserConfigFromFile() {
        if (!FileUtil.exist(this.userConfigPath)) {
            return null;
        }
        String userConfigStr = FileUtil.readString(userConfigPath, Charset.forName("utf-8"));
        try {
            return JsonUtil.json2obj(userConfigStr, UserConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取用户配置文件发生错误：{}", e.getMessage());
            return null;
        }
    }

    public void saveUserConfig(UserConfig userConfig) throws IOException {
        if (userConfig == null) {
            throw new ServiceException("不能写入空的用户配置");
        }
        String configStr = JsonUtil.obj2json(userConfig);
        File userConfigFile = new File(this.userConfigPath);
        if (userConfigFile.exists()) {
            userConfigFile.delete();
        }
        Files.createParentDirs(userConfigFile);
        userConfigFile.createNewFile();
        FileUtil.writeFromStream(new ByteArrayInputStream(configStr.getBytes(Charset.forName("utf-8"))), userConfigFile);
    }

    public String uploadTemplate(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String saveFileName = fileName.substring(0, fileName.lastIndexOf(fileSuffix)) + DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String savePath = PathUtil.joinPath(getTemplateStoreDir(), saveFileName);
        log.info("模板上传路径为：{}", savePath);
        File saveFile = new File(savePath);
        try {
            FileUtil.writeFromStream(file.getInputStream(), saveFile);
        } catch (IOException e) {
            throw new ServiceException("上传模板文件失败", e);
        }
        return Constant.RESOURCE_PREFIX_FILE + savePath;
    }

}
