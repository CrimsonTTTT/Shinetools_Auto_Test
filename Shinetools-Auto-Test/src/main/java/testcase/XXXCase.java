package testcase;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author Graycat.
 * @CreateTime 2023/10/21 16:20
 * @Descripe
 */
public class XXXCase {
    String excelPath = "D:\\TestCode\\ExcelTest.xlsx";

    @Test
    public void testSomeFun() throws IOException {
        try {
            // 读取现有的工作簿
            FileInputStream fileIn = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fileIn);
            fileIn.close();

            // 获取要操作的工作表（这里假设要操作第一个工作表）
            Sheet sheet = workbook.getSheetAt(0);

            // 创建新行并在其中插入数据
            int rowIndex = sheet.getLastRowNum() + 1; // 下一个可用的行号
            Row row = sheet.createRow(rowIndex);

            // 在新行的第一个单元格（A列）插入字符串数据
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("New Data");

            // 在新行的第二个单元格（B列）插入数字数据
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(456);

            // 在新行的第三个单元格（C列）插入日期数据
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(new java.util.Date());
            cell3.setCellStyle(dateCellStyle);

            // 将更新后的工作簿保存回文件
            FileOutputStream fileOut = new FileOutputStream(excelPath);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Data written to the Excel file successfully.");

            // 关闭工作簿
            workbook.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
