package com.msc.fix.lisa.domain.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
public class BasePo {
    @Column(
            name = "create_time"
    )
    @ApiModelProperty("创建时间，格式为yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ExcelIgnore
    private Date createTime;
    @Column(
            name = "create_user"
    )
    @JsonIgnore
    @ExcelIgnore
    private String createUser;
    @Column(
            name = "update_time"
    )
    @ApiModelProperty("修改时间，格式为yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ExcelIgnore
    private Date updateTime;
    @Column(
            name = "update_user"
    )
    @JsonIgnore
    @ExcelIgnore
    private String updateUser;
    @JsonIgnore
    @ExcelIgnore
    private Integer yn;
}