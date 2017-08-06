package com.xcjy.web.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tupeng on 2017/7/24.
 */
public abstract class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 获取excel工作页
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static XSSFSheet getSheet(InputStream inputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        return workbook.getSheetAt(0);
    }

    /**
     * 获取excel表头
     *
     * @param sheet
     * @param headerRow
     * @param maxColumn
     * @return
     */
    public static Set<String> getHeader(HSSFSheet sheet, Integer headerRow, Integer maxColumn) {
        Set<String> headers = new LinkedHashSet<>();
        HSSFRow row = sheet.getRow(headerRow);
        for (int column = 0; column < maxColumn; column++) {
            headers.add(row.getCell(column).getStringCellValue());
        }
        return headers;
    }

    public static void getData(List<Map<String, String>> resultList, XSSFSheet sheet, Integer headerBegin, Integer dataBegin, Integer maxColumn) {
        Map<String, String> dataMap = new HashMap<>();
        Boolean hasData = false;
        XSSFRow headerRow = sheet.getRow(headerBegin);
        XSSFRow dataRow = sheet.getRow(dataBegin);
        logger.info("获取excel数据，第{}行", dataBegin + 1);
        for (int column = 0; column < maxColumn; column++) {
            String key = headerRow.getCell(column).getStringCellValue();
            if(null == dataRow) {
                break;
            }
            String val = dataRow.getCell(column).getStringCellValue();
            dataMap.put(key, val);
            if (StringUtils.isNotBlank(val)) {
                hasData = true;
            }
        }
        if (hasData) {
            resultList.add(dataMap);
            getData(resultList, sheet, headerBegin, dataBegin + 1, maxColumn);
        }
    }

    public static <T> List<T> getResultList(List<Map<String, String>> dataMapList, Class<T> clss) {
        List<T> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataMapList)) {
            for (Map<String, String> dataMap : dataMapList) {
                dataList.add(ExcelHandler.getExcelObject(clss, dataMap));
            }
        }
        return dataList;
    }

}
