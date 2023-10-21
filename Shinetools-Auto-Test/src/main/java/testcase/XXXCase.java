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
            // ��ȡ���еĹ�����
            FileInputStream fileIn = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fileIn);
            fileIn.close();

            // ��ȡҪ�����Ĺ������������Ҫ������һ��������
            Sheet sheet = workbook.getSheetAt(0);

            // �������в������в�������
            int rowIndex = sheet.getLastRowNum() + 1; // ��һ�����õ��к�
            Row row = sheet.createRow(rowIndex);

            // �����еĵ�һ����Ԫ��A�У������ַ�������
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("New Data");

            // �����еĵڶ�����Ԫ��B�У�������������
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(456);

            // �����еĵ�������Ԫ��C�У�������������
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(new java.util.Date());
            cell3.setCellStyle(dateCellStyle);

            // �����º�Ĺ�����������ļ�
            FileOutputStream fileOut = new FileOutputStream(excelPath);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Data written to the Excel file successfully.");

            // �رչ�����
            workbook.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
