package com.msc.fix.lisa.base;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.extension.BizScenario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * AbstractCommand
 * 公共
 * 新增
 * 修改
 *
 * @author lzp
 * @date 2020-7-7 18:52
 */
@Data
public abstract class AbstractCommand extends Command {


    /**
     * 当前用户
     */
    @ApiModelProperty(hidden = true)
    private String account;

    /**
     * 当前用户
     */
    @ApiModelProperty(hidden = true)
    private String accountId;

    /**
     * 当前用户
     */
    @ApiModelProperty(hidden = true)
    private String pin;

    /**
     * 商户编号
     */
    @ApiModelProperty(hidden = true)
    private String sellerNo;

    /**
     * 商家名称
     */
    @ApiModelProperty(hidden = true)
    private String sellerName;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private String updateUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * yn
     */
    @ApiModelProperty(hidden = true)
    private Integer yn;

    /**
     * 父类Command的属性，不需要ApiModelProperty
     */
    @ApiModelProperty(hidden = true)
    private BizScenario bizScenario;


    /**
     * initCreate
     *
     * @param accountId accountId
     * @return void
     */
    public void initCreate(String accountId) {
        this.createTime = new Date();
        this.updateTime = this.createTime;
        this.createUser = accountId;
        this.updateUser = accountId;
        this.yn = YnValueEnum.yesCode();

        this.pin = accountId;
        this.accountId = accountId;
    }

    /**
     * initUpdate
     *
     * @param accountId accountId
     * @return void
     */
    public void initUpdate(String accountId) {
        this.updateTime = new Date();
        this.updateUser = accountId;
        this.pin = accountId;
        this.accountId = accountId;
    }

    /**
     * initCreate
     *
     * @param accountBaseInfo accountBaseInfo
     * @return void
     */
    /*public void initCreate(AccountBaseInfo accountBaseInfo) {
        this.createTime = new Date();
        this.updateTime = this.createTime;
        this.createUser = accountBaseInfo.getAccount();
        this.updateUser = accountBaseInfo.getAccount();
        this.yn = YnValueEnum.yesCode();

        this.pin = accountBaseInfo.getAccountId();
        this.accountId = accountBaseInfo.getAccountId();
        this.account = accountBaseInfo.getAccount();
    }*/

    /**
     * initUpdate
     *
     * @param accountBaseInfo accountBaseInfo
     * @return void
     */
    /*public void initUpdate(AccountBaseInfo accountBaseInfo) {
        this.updateTime = new Date();
        this.updateUser = accountBaseInfo.getAccount();

        this.pin = accountBaseInfo.getAccountId();
        this.accountId = accountBaseInfo.getAccountId();
        this.account = accountBaseInfo.getAccount();
    }*/
}
