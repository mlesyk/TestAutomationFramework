package org.mlesyk.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Maks.
 */
public class ExcelReader {

    private static final Logger log = LogManager.getLogger(ExcelReader.class);

    private static final int FIRST_SHEET = 0;
    private static final int FIRST_ROW = 0;

    public static List<Object[]> readExcelData(String fileName) {
        Map<String, List<String>> columns = new LinkedHashMap<String, List<String>>();
        log.debug("Opening file {}", fileName);
        File file = new File(ExcelReader.class.getResource(fileName).getFile());

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }
            readColumnsData(workbook, columns);
        } catch (IOException e) {
            log.error("Exception thrown while reading file " + fileName, e);
        }

        List<Object[]> result = new ArrayList<Object[]>();
        transposeColumnsDataToTupleList(columns, result);
        return result;
    }

    private static void readColumnsData(Workbook workbook, Map<String, List<String>> columns) {
        Sheet sheet = workbook.getSheetAt(FIRST_SHEET);
        DataFormatter dataFormatter = new DataFormatter();
        for (Cell cell : sheet.getRow(FIRST_ROW)) {
            List<String> column = columns.computeIfAbsent(cell.getStringCellValue(), s -> new ArrayList<String>());
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                } else {
                    column.add(dataFormatter.formatCellValue(row.getCell(cell.getColumnIndex())));
                }
            }
        }
    }

    private static void transposeColumnsDataToTupleList(Map<String, List<String>> columns, List<Object[]> result) {
        for (int i = 0; i < columns.values().iterator().next().size(); i++) {
            List<String> line = new ArrayList<String>();
            for (Map.Entry<String, List<String>> column : columns.entrySet()) {
                line.add(column.getValue().get(i));
            }
            result.add(line.toArray());
        }
    }
}

