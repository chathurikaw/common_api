package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
    private static FileInputStream fi;
    private static FileOutputStream fo;
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    private static Cell cell;
    private static CellStyle style;

    public static void setExcelFile(String filePath, String sheetName) throws IOException {
        fi = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
    }

    public static int getRowCount() {
        return sheet.getLastRowNum();
    }

    public static int getCellCount(int rownum) {
        row = sheet.getRow(rownum);
        return row.getLastCellNum();
    }

    public static String getCellData(int rownum, int colnum) {
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);
        String data;
        try {
            data = cell.getStringCellValue();
        } catch (Exception e) {
            data = "";
        }
        return data;
    }

    public static void setCellData(String filePath, int rownum, int colnum, String data) throws IOException {
        row = sheet.getRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue(data);
        fo = new FileOutputStream(filePath);
        workbook.write(fo);
        fo.close();
    }

    public static void fillGreenColor(String filePath, int rownum, int colnum) throws IOException {
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fo = new FileOutputStream(filePath);
        workbook.write(fo);
        fo.close();
    }

    public static void fillRedColor(String filePath, int rownum, int colnum) throws IOException {
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fo = new FileOutputStream(filePath);
        workbook.write(fo);
        fo.close();
    }

    public static void closeWorkbook() throws IOException {
        workbook.close();
        fi.close();
    }
}
