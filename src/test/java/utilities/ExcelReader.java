
package utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static Object[][] readExcel(
            String filePath, String sheetName) {

        List<Object[]> data = new ArrayList<>();

        try (FileInputStream fis =
                     new FileInputStream(filePath);
             Workbook workbook =
                     WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException(
                        "Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();

            // Skip the header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String username = "";
                String password = "";

                Cell usernameCell = row.getCell(
                        0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                Cell passwordCell = row.getCell(
                        1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                if (usernameCell != null) {
                    username = formatter.formatCellValue(
                            usernameCell).trim();
                }

                if (passwordCell != null) {
                    password = formatter.formatCellValue(
                            passwordCell).trim();
                }

                data.add(new Object[]{username, password});
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel file: " + filePath, e);
        }

        return data.toArray(new Object[0][]);
    }
}
