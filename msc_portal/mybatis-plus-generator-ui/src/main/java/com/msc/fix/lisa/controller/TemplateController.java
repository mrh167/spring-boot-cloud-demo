package com.msc.fix.lisa.controller;

import com.msc.fix.lisa.common.Result;
import com.msc.fix.lisa.common.ResultGenerator;
import com.msc.fix.lisa.common.ServiceException;
import com.msc.fix.lisa.dto.OutputFileInfo;
import com.msc.fix.lisa.dto.UserConfig;
import com.msc.fix.lisa.service.OutputFileInfoService;
import com.msc.fix.lisa.service.TemplateService;
import com.msc.fix.lisa.service.UserConfigStore;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/template")
public class TemplateController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserConfigStore userConfigStore;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private OutputFileInfoService outputFileInfoService;

    @GetMapping("/download")
    public void download(HttpServletResponse res, @RequestParam String fileType) throws UnsupportedEncodingException, FileNotFoundException {
        if (Strings.isNullOrEmpty(fileType)) {
            log.error("fileType不能为空");
            return;
        }
        UserConfig userConfig = userConfigStore.getUserConfigFromFile();
        if (userConfig == null) {
            InputStream tplIn = templateService.getBuiltInTemplate(fileType);
            download(res, tplIn);
            return;
        }
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        for (OutputFileInfo fileInfo : fileInfos) {
            if (fileType.equals(fileInfo.getFileType())) {
                if (fileInfo.isBuiltIn()
                        && Strings.isNullOrEmpty(fileInfo.getTemplatePath())) {
                    InputStream tplIn = templateService.getBuiltInTemplate(fileType);
                    download(res, tplIn);
                } else {
                    File tplFile = new File(fileInfo.getTemplatePath());
                    if (tplFile.exists()) {
                        download(res, new FileInputStream(tplFile));
                    } else {
                        throw new ServiceException("未找到模板文件：" + fileInfo.getTemplatePath());
                    }
                }
                break;
            }
        }
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        Map<String, Object> params = Maps.newHashMap();
        String storePath = userConfigStore.uploadTemplate(file);
        params.put("templatePath", storePath);
        params.put("templateName", file.getOriginalFilename());
        return ResultGenerator.genSuccessResult(params);
    }

    private void download(HttpServletResponse res, InputStream tplIn) throws UnsupportedEncodingException {
        if (tplIn != null) {
            res.setCharacterEncoding("utf-8");
            res.setContentType("multipart/form-data;charset=UTF-8");
            try {
                OutputStream os = res.getOutputStream();
                byte[] b = new byte[2048];
                int length;
                while ((length = tplIn.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (tplIn != null) {
                    try {
                        tplIn.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }


}
