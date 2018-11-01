package com.ipaylinks.poss.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class SimpleExcelGenerator {
    private static final int DEFAULT_WIDTH = 2560;

    private static final String DEFAULT_SHEETNAME = "Sheet 1";

    public final static String YYYYMMDDHHMMSS3 = "yyyy-MM-dd HH:mm:ss";

    public SimpleExcelGenerator() {
    }

    private static Object[][] buildVals(String[] fields, List<? extends Object> valueList) throws Exception {
        if (valueList == null || valueList.size() == 0) {
            return null;
        }
        Object[][] vals = new Object[valueList.size()][fields.length];

        for (int i = 0; i < vals.length; ++i) {
            Object obj = valueList.get(i);

            for (int k = 0; k < fields.length; ++k) {
                vals[i][k] = getValue(obj, fields[k]);
            }
        }

        return vals;
    }

    public static Workbook generateGrid(String sheetName, String[] headers, String[] fields, List valueList) throws Exception {
        return generateGrid(sheetName, headers, buildVals(fields, valueList));
    }

    public static Workbook generateGrid(String sheetName, String[] headers, Object[][] content) {
        return generateGrid((Workbook) (new HSSFWorkbook()), (String) sheetName, headers, (Object[][]) content);
    }

    public static Workbook generateLargeGrid(String sheetName, String[] headers, String[] fields, List<? extends Object> valueList) throws Exception {
        return generateLargeGrid(sheetName, headers, buildVals(fields, valueList));
    }

    public static Workbook generateLargeGrid(String sheetName, String[] headers, Object[][] content) {
        return generateGrid((Workbook) (new SXSSFWorkbook()), (String) sheetName, headers, (Object[][]) content);
    }

    public static Workbook generateGrid(Workbook workbook, String sheetName, String[] headers, Object[][] content) {
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = "Sheet 1";
        }

        int rowNumber = 0;
        Sheet sheet = workbook.createSheet(sheetName);
        Row headRow = sheet.createRow(rowNumber);
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight((short) 700);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        int[] widths = new int[headers.length];

        int i;
        for (i = 0; i < headers.length; i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellValue(StringUtils.isEmpty(headers[i]) ? "" : headers[i]);
            cell.setCellStyle(headerStyle);
            widths[i] = headers[i] == null ? 2560 : headers[i].getBytes().length * 256;
        }
        rowNumber++;
        if (content != null && content.length > 0) {
            for (i = 0; i < content.length; i++) {
                Row row = sheet.createRow(rowNumber + i);

                for (int k = 0; k < headers.length; ++k) {
                    Cell cell = row.createCell(k);
                    cell.setCellType(1);
                    cell.setCellValue(String.valueOf(content[i][k] == null ? "" : content[i][k]));
                    int len = content[i][k] == null ? 2560 : String.valueOf(content[i][k]).getBytes().length * 256;
                    if (len > widths[k]) {
                        widths[k] = len;
                    }
                }
            }
        }

        for (i = 0; i < widths.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }

    private static Object getValue(Object obj, String fieldName) throws Exception {
        Object result = null;
        if (obj instanceof Map) {
            Map<?, ?> m = (Map) obj;
            result = m.get(fieldName);
        } else if (fieldName.indexOf(".") != -1) {
            String f1 = fieldName.substring(0, fieldName.indexOf("."));
            String f2 = fieldName.substring(fieldName.indexOf(".") + 1);
            Field f = obj.getClass().getDeclaredField(f1);
            f.setAccessible(true);
            Object newObj = f.get(obj);
            result = getValue(newObj, f2);
        } else {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            result = f.get(obj);
            if (result instanceof Date) {
                result = DateFormatUtils.format((Date) result, YYYYMMDDHHMMSS3);
            }
        }

        return result;
    }
}
