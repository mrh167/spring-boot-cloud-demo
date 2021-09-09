package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.AbstractCommand;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/8
 * Time: 12:22
 * Description: No Description
 */
@Data
public class UpdateStatusCmd extends AbstractCommand {

    private Long id;

    private Integer status;
}
