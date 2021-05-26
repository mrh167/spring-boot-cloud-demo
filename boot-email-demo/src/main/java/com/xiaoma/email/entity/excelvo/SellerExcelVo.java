package com.xiaoma.email.entity.excelvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/5/26
 * Time: 10:34
 * Description: No Description
 */
@Data
@ColumnWidth(20)
public class SellerExcelVo {
    @ExcelProperty(value = "商家编号")
    private String sellerName;
    @ExcelProperty(value = "商家编码")
    private String sellerNo;
    @ExcelProperty(value = "签约区域")
    private String registerRegion;
    @ExcelProperty(value = "区域销售")
    private String regionAccount;
    @ExcelProperty(value = "申请人pin")
    private String applyAccount;
    @ExcelProperty(value = "申请时间")
    private Date applyTime;
}
