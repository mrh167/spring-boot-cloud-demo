package com.msc.mall.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * ResultTip
 * 统一结果明细提示
 *
 * @author lzp
 * @version 1.0
 * @date 2020/7/24
 */
@ApiModel(value = "ResultTip", description = "统一结果明细提示")
public class ResultTip {
    /**
     * 详细信息错误码
     */
    public static final String DETAIL_ERROR = "DETAIL_ERROR";

    /**
     * key: 行号
     */
    private static final String ROW_NO = "rowNo";

    /**
     * msg: 错误信息
     */
    private static final String MSG = "msg";

    /**
     * 错误明细上限
     */
    private static Integer ERROR_UP_LIMIT;

    /**
     * 成功条数
     */
    @ApiModelProperty("成功条数")
    private Integer successCount = 0;

    /**
     * 错误条数
     */
    @ApiModelProperty("错误条数")
    private Integer errorCount = 0;

    /**
     * 最后错误行号
     */
    @ApiModelProperty("最后错误行号")
    private Integer lastErrorRowNo = -1;

    /**
     * 批量操作
     * 错误明细
     */
    @ApiModelProperty("错误明细")
    private Map<String, Map<String, Object>> errorTipMap = new HashMap<>(128);

    /**
     * 批量上传
     * 错误明细
     */
    private TreeMap<Integer, Map<String, Object>> errorRowTreeMap = new TreeMap<>();


    /**
     * ResultTip 构造
     */
    public ResultTip() {
        ERROR_UP_LIMIT = 100;
    }

    /**
     * ResultTip 构造
     *
     * @param errorUpLimit 最大错误限制
     */
    public ResultTip(int errorUpLimit) {
        ERROR_UP_LIMIT = errorUpLimit;
    }

    /**
     * 总条数展示
     *
     * @param
     * @return java.lang.Integer
     */
    public Integer getTotal() {
        return successCount + errorCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * 错误明细
     *
     * @return
     */
    public List<Map<String, Object>> getDetaileds() {
        if (!errorTipMap.isEmpty()) {
            return errorTipMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        return errorRowTreeMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 成功标识
     *
     * @return
     */
    public boolean success() {
        return !hasError();
    }

    /**
     * 是否有错误明细
     *
     * @return
     */
    public boolean hasError() {
        return errorCount > 0;
    }

    /**
     * 重置成功数
     */
    public void resetSuccessCount() {
        this.successCount = 0;
    }

    /**
     * 自增
     *
     * @param
     * @return void
     */
    public void increaseSuccess() {
        successCount++;
    }


    /**
     * 添加错误明细
     *
     * @param rowNo    rowNo
     * @param errorMsg errorMsg
     * @return void
     */
    public void addErrorMsg(Integer rowNo, String errorMsg) {
        lastErrorRowNo = rowNo;
        if (errorCount > ERROR_UP_LIMIT) {
            return;
        }

        Map<String, Object> errorRow = errorRowTreeMap.get(rowNo);
        if (errorRow != null) {
            errorRow.put(MSG, errorRow.get(MSG) + ", " + errorMsg);
        } else {
            errorRow = new HashMap<>(2);
            errorRow.put(ROW_NO, rowNo);
            errorRow.put(MSG, errorMsg);
            errorRowTreeMap.put(rowNo, errorRow);
            errorCount++;
        }
    }

    /**
     * 添加错误明细
     *
     * @param tip      tip
     * @param errorMsg errorMsg
     * @return void
     */
    public void addErrorMsg(String tip, Long id, String errorCode, String errorMsg) {
        if (errorCount > ERROR_UP_LIMIT) {
            return;
        }

        Map<String, Object> errorTip = new HashMap<>(4);
        errorTip.put("tip", tip);
        errorTip.put("id", id);
        errorTip.put("errorCode", errorCode);
        errorTip.put(MSG, errorMsg);
        putErrorMsgMap(tip, errorTip);
        errorCount++;
    }

    /**
     * 添加错误明细
     *
     * @param tip      tip
     * @param errorMsg errorMsg
     * @return void
     */
    public void addErrorMsg(String tip, String errorMsg) {
        if (errorCount > ERROR_UP_LIMIT) {
            return;
        }
        Map<String, Object> errorTip = new HashMap<>(2);
        errorTip.put("tip", tip);
        errorTip.put(MSG, errorMsg);
        putErrorMsgMap(tip, errorTip);
        errorCount++;
    }

    /**
     * 添加错误明细
     *
     * @param errorMsg errorMsg
     * @return void
     */
    public void addErrorMsg(String errorMsg) {
        if (errorCount > ERROR_UP_LIMIT) {
            return;
        }
        Map<String, Object> uploadFileErrorMsgTip = new HashMap<>(1);
        uploadFileErrorMsgTip.put(MSG, errorMsg);
        putErrorMsgMap(null, uploadFileErrorMsgTip);
        errorCount++;
    }

    /**
     * 根据tip 添加错误信息
     *
     * @param tip
     * @param errorTip
     */
    private void putErrorMsgMap(String tip, Map<String, Object> errorTip) {
        if (StringUtils.isBlank(tip)) {
            errorTipMap.put(String.valueOf(System.nanoTime()), errorTip);
        } else {
            Map<String, Object> originMap = errorTipMap.get(tip);
            if (originMap != null) {
                originMap.put(MSG, originMap.get(MSG) + ", " + errorTip.get(MSG));
            } else {
                errorTipMap.put(tip, errorTip);
            }
        }
    }

    public boolean hasErrorByRowNo(Integer rowNo) {
        return errorRowTreeMap.get(rowNo) != null;
    }

    public Integer lastErrorRowNo() {
        return lastErrorRowNo;
    }

    public boolean hasErrorByLastErrorRowNo(Integer lastErrorRowNo) {
        return this.lastErrorRowNo.equals(lastErrorRowNo);
    }
}
