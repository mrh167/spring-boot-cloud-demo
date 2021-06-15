package com.xiaoma.email.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * EasyExcelUtil
 * excel工具
 *
 * @author lzp
 * @version 1.0
 * @date 2020/4/12
 */
public class EasyExcelUtil {

    /**
     * 下载reponse设置
     *
     * @param response
     * @param fileName
     * @throws Exception
     */
    public static void downloadResponseSet(HttpServletResponse response, String fileName) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "filename");
        response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ExcelTypeEnum.XLSX.getValue()
                + "; filename*=utf-8''" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ExcelTypeEnum.XLSX.getValue());
        response.addHeader("Pragma", "No-cache");
        response.addHeader("filename", JdStringUtils.urlEncode(fileName + ExcelTypeEnum.XLSX.getValue()));
    }

    /**
     * 下载reponse设置
     *
     * @param response response
     * @param fileName fileName
     * @param ext      ext
     */
    public static void downloadResponseSet(HttpServletResponse response, String fileName, ExcelTypeEnum ext) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "filename");
        response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ExcelTypeEnum.XLSX.getValue()
                + "; filename*=utf-8''" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ext.getValue());
        response.addHeader("Pragma", "No-cache");
        response.addHeader("filename", JdStringUtils.urlEncode(fileName + ext.getValue()));
    }


    /**
     * 导出样式设置
     *
     * @return
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = buildHeadCellStyle();
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = buildContentCellStyle();
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }


    /**
     * 根据fontColor和bgColor导出样式
     *
     * @param fontColor
     * @param bgColor
     * @return
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(short fontColor, short bgColor) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = buildHeadCellStyle(fontColor, bgColor);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = buildContentCellStyle();
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }


    /**
     * 根据字体颜色和背景颜色导出样式
     *
     * @param fontColor
     * @param bgColor
     * @return
     */
    public static WriteCellStyle buildHeadCellStyle(short fontColor, short bgColor) {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(bgColor);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setFontName("微软雅黑");
        headWriteFont.setBold(true);
        headWriteFont.setColor(fontColor);
        headWriteCellStyle.setWriteFont(headWriteFont);
        return headWriteCellStyle;
    }


    /**
     * buildHeadCellStyle
     *
     * @param
     * @return
     */
    private static WriteCellStyle buildHeadCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setFontName("微软雅黑");
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        return headWriteCellStyle;
    }


    /**
     * buildContentCellStyle
     *
     * @return
     */
    private static WriteCellStyle buildContentCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 下边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 左边框
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        // 上边框
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        // 右边框
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        // 水平对齐方式
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return contentWriteCellStyle;
    }

    /**
     * 导出excel
     *
     * @param outputStream      输出流
     * @param dataList          导出的数据
     */
    public static void writeExcelWithModel(OutputStream outputStream,List<? extends Object> dataList,List<? extends Object> accountsInfo,Class<? extends Object> classT) {

        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        WriteSheet sellerSheet = EasyExcel.writerSheet(0,"商家开通待审核信息")
                .head(classT)
                .registerWriteHandler(getHorizontalCellStyleStrategy(IndexedColors.BLACK.index, IndexedColors.LIME.index))
                .build();
        excelWriter.write(dataList,sellerSheet);
        WriteSheet accountSheet = EasyExcel.writerSheet(1,"账号申请待审核信息")
                .head(classT)
                .registerWriteHandler(getHorizontalCellStyleStrategy(IndexedColors.BLACK.index, IndexedColors.LIME.index))
                .build();
        excelWriter.write(accountsInfo,accountSheet);
        excelWriter.finish();
        //        // 头的策略
//        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
//        // 单元格策略
//        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//        // 初始化表格样式
//        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//
//        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream,classT).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
//        if (null != cellWriteHandlers && cellWriteHandlers.length > 0) {
//            for (int i = 0; i < cellWriteHandlers.length; i++) {
//                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
//            }
//        }
//        // 开始导出
//        excelWriterSheetBuilder.doWrite(dataList);
    }
}

