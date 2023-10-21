package util;

import bo.ExcelBo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/22 18:50
 * @Descripe
 */
public class ExcelUtlis {

    public String EXCEL_PATH;

    /**
     *  ��ȡexcel�е�ʵ����BO
     */
    public List<ExcelBo> readExcel(String path ) throws IOException {
        // ����һ�� FileInputStream ����ָ�� Excel �ļ�
        FileInputStream inputStream = new FileInputStream(path);

        // ����һ�� Workbook ����
        Workbook workbook = new XSSFWorkbook(inputStream);

        // ��ȡ��һ��������
        Sheet sheet = workbook.getSheetAt(0);

        List<ExcelBo> result = new ArrayList<>();

        // �����������е�������, ��һ��Ϊ��ͷ���������ӵڶ��п�ʼ
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // ��ȡһ��
            Row row = sheet.getRow(i);
            // ��¼һ��
            ExcelBo rowTemp = new ExcelBo();
            // ��¼����·��
            List<String> funPath = new ArrayList<>();

            // �������е�������
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                // ��ȡһ��
                Cell cell = row.getCell(j);

                // ���������ж����Ͳ���ֵ
                if( j == 0 ){                       // ��1�У����ܱ��
                    rowTemp.setTestSN(cell.getNumericCellValue());
                }else if ( j >= 1 && j <= 6){       // ��2�е���7�У����ܵ�·��
                    // ��¼��2��7�е�·��
                    funPath.add(cell.getStringCellValue());
                }else if( j == 7 ){                 // ��8�У���������
                    rowTemp.setType(cell.getStringCellValue());
                }else if( j == 8 ){                 // ��9�У�������Χ
                    // �ж��Ƿ�Ϊ�գ���/������Ϊ��
                    if ( cell.getStringCellValue().equals("/") ){
                        rowTemp.setRangeHigh(0);
                        rowTemp.setRangeLow(0);
                    }else {
                        double high = setCellRange(cell.getStringCellValue(), 1);
                        double low = setCellRange(cell.getStringCellValue(), 2);
                        rowTemp.setRangeHigh(high);
                        rowTemp.setRangeLow(low);
                    }
                }else if ( j == 9 ){                // ��10�У�������ѡѡ��ֵ
                    rowTemp.setSelectValue(cell.getStringCellValue());
                }else if ( j == 10 ){                // ��11�У��Ĵ�������
                    rowTemp.setRegisterType((int) cell.getNumericCellValue());
                }else if ( j == 11 ){                // ��12�У��Ĵ�����ַ
                    rowTemp.setRegister((int) cell.getNumericCellValue());
                }else if ( j == 12 ){                // ��13�У��Ĵ�������
                    rowTemp.setRegisterLength((int) cell.getNumericCellValue());
                }
                else {
                    rowTemp.setRemark("Excel���������⣬���д���"+j+"��");
                }
            }
            rowTemp.setPath(funPath);
            result.add(rowTemp);
        }

        // �ر� FileInputStream ����
        inputStream.close();

        return result;
    }


    /**
     * Description:  ������excelд���У�ֻд����
     * @param rowBo bo.ExcelBo
     * @return void
     * @author Graycat. 2023/9/28 16:35
     */
    public void writeExcelRowResult( ExcelBo rowBo ) throws IOException {

        FileInputStream fis = new FileInputStream(EXCEL_PATH);

        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);

        int rowIndex = (int)rowBo.getTestSN() ;

        Row row = sheet.getRow(rowIndex);

        Cell cell = row.createCell(13); // �±��0��ʼ

        cell.setCellValue(rowBo.getResult());

        // �����º�Ĺ�����������ļ�
        FileOutputStream fileOut = new FileOutputStream(EXCEL_PATH);

        workbook.write(fileOut);

        workbook.close();
        fis.close();
        fileOut.close();
    }

    /**
     *  ��ȡ��ǰ�б�����
     * */
    public void getConfigDataList(){

    }

    public void setExcelPath( String path ){
        EXCEL_PATH = path;
    }

    /**
     * ������Χ��type=1ʱΪ���ޣ�����Ϊ����
     * */
    public double setCellRange( String range, int type ) {
        // ʹ�ö��ŷָ��ַ���
        String[] parts = range.split(",");
        double value1 = 0, value2 = 0;
        if (parts.length == 2) {
            try {
                // ���ָ����ַ���ת��Ϊ double
                value1 = Double.parseDouble(parts[0]);
                value2 = Double.parseDouble(parts[1]);

            } catch (NumberFormatException e) {
                System.err.println("�޷�����Ϊ double ֵ��");
            }
        } else {
            System.err.println("�����ַ�����ʽ����ȷ��");
        }
        if (type == 1) {
            return value1;
        }else return value2;
    }


}
