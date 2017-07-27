package com.xcjy.web.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tupeng on 2017/7/24.
 */
public abstract class ExcelUtil {

    /**
     * 获取excel工作页
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static HSSFSheet getSheet(InputStream inputStream) throws IOException {
        POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
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

    public static void getData(List<Map<String, String>> resultList, HSSFSheet sheet, Integer headerBegin, Integer dataBegin, Integer maxColumn) {
        Map<String, String> dataMap = new HashMap<>();
        Boolean hasData = false;
        HSSFRow headerRow = sheet.getRow(headerBegin);
        HSSFRow dataRow = sheet.getRow(dataBegin);
        for (int column = 0; column < maxColumn; column++) {
            String key = headerRow.getCell(column).getStringCellValue();
            String val = dataRow.getCell(column).getStringCellValue();
            dataMap.put(key, val);
            if (StringUtils.isNotBlank(val)) {
                hasData = true;
            }
        }
        if (hasData) {
            resultList.add(dataMap);
            getData(resultList, sheet, headerBegin, dataBegin, maxColumn);
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
