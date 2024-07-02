package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    private static final String FILE_PATH = "src/test/resources/test-data/UserData.xlsx";
    private static final String SHEET_NAME = "Sheet1";
    
	 @DataProvider(name = "UserData")
	    public static Object[][] getUserData() throws Exception {
	        String filePath = FILE_PATH;
	        String sheetName = SHEET_NAME;

	        // Initialize Excel file
	        XLUtils.setExcelFile(filePath, sheetName);

	        int rowCount = XLUtils.getRowCount();
	        int colCount = XLUtils.getCellCount(0); // Assuming all rows have the same number of columns

	        Object[][] data = new Object[rowCount][colCount];

	        for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header row
	            for (int j = 0; j < colCount; j++) {
	                data[i - 1][j] = XLUtils.getCellData(i, j);
	            }
	        }
	        // Close workbook after reading data
	        XLUtils.closeWorkbook();

	        return data;
	    }
	 
	 @DataProvider(name = "Usernames")
	    public static Object[][] getUsernamesProvider() throws IOException {
	        String filePath = FILE_PATH;
	        String sheetName = SHEET_NAME;

	        XLUtils.setExcelFile(filePath, sheetName);

	        int rowCount = XLUtils.getRowCount();

	        Object[][] data = new Object[rowCount][1];

	        for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header row
	            data[i - 1][0] = XLUtils.getCellData(i, 1); // Username is in the second column (index 1)
	        }

	        XLUtils.closeWorkbook();

	        return data;
	    }
}
