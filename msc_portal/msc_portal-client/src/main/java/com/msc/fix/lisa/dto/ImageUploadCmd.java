package com.msc.fix.lisa.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/3
 * Time: 16:09
 * Description: No Description
 */
@Data
public class ImageUploadCmd extends Command {

    private MultipartFile file;
}
