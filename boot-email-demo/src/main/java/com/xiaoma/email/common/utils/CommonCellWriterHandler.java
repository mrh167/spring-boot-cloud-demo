package com.xiaoma.email.common.utils;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.HashMap;
import java.util.List;

/**
 * @author qiuchang
 * @date 2020/9/9 0:04
 */
public class CommonCellWriterHandler implements CellWriteHandler {

    /**
     * 操作列
     */
    private List<Integer> columnIndexs;

    /**
     * 颜色
     */
    private Short colorIndex;

    /**
     * 批注<列的下标,批注内容>
     */
    private HashMap<Integer, String> annotationsMap;

    /**
     * 下拉框值
     */
    private HashMap<Integer, String[]> dropDownMap;

    public CommonCellWriterHandler() {
    }

    public CommonCellWriterHandler(HashMap<Integer, String[]> dropDownMap) {
        this.dropDownMap = dropDownMap;
    }

    public CommonCellWriterHandler(List<Integer> columnIndexs, Short colorIndex) {
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
    }

    public CommonCellWriterHandler(List<Integer> columnIndexs, Short colorIndex, HashMap<Integer, String> annotationsMap) {
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
        this.annotationsMap = annotationsMap;
    }

    public CommonCellWriterHandler(List<Integer> columnIndexs, Short colorIndex, HashMap<Integer, String> annotationsMap, HashMap<Integer, String[]> dropDownMap) {
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
        this.annotationsMap = annotationsMap;
        this.dropDownMap = dropDownMap;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean isHead) {
        if (isHead && cell.getStringCellValue().contains("*")) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            WriteCellStyle writeCellStyle = EasyExcelUtil.buildHeadCellStyle(IndexedColors.RED.getIndex(), IndexedColors.PALE_BLUE.getIndex());
            CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, writeCellStyle);
            cell.setCellStyle(cellStyle);
            Sheet sheet = writeSheetHolder.getSheet();
            if (null != annotationsMap && annotationsMap.containsKey(cell.getColumnIndex())) {
                Drawing<?> drawing = sheet.createDrawingPatriarch();
                // 批注内容
                String context = annotationsMap.get(cell.getColumnIndex());
                // 创建绘图对象
                Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), 0, (short) 5, 5));
                comment.setString(new XSSFRichTextString(context));
                cell.setCellComment(comment);
            }

            if (null != dropDownMap &&
                    !dropDownMap.isEmpty() &&
                    dropDownMap.containsKey(cell.getColumnIndex())) {
                String[] datas = dropDownMap.get(cell.getColumnIndex());
                DataValidationHelper dvHelper = sheet.getDataValidationHelper();
                DataValidationConstraint dvConstraint = dvHelper
                        .createExplicitListConstraint(datas);
                CellRangeAddressList addressList = null;
                DataValidation validation = null;
                for (int i = 1; i < 1000; i++) {
                    addressList = new CellRangeAddressList(i, i, cell.getColumnIndex(), cell.getColumnIndex());
                    validation = dvHelper.createValidation(
                            dvConstraint, addressList);
                    sheet.addValidationData(validation);
                }
            }
        }
    }
}
