package util;

import bo.ExcelBo;
import bo.SingleReadDataBo;
import config.LoggerLoad;
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

    public String EXCEL_PATH;               // �������ļ�
    public String HOME_DATA_EXCEL_PATH = "D:\\TestCode\\Auto_HomeData_SPH10000TL-HU.xlsx";     // ��ҳ���ݵ��ļ�

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
                        rowTemp.setRangeHigh(low);
                        rowTemp.setRangeLow(high);
                        LoggerLoad.debug("excel����˳��" + high + "  low:" + low);
                    }
                }else if ( j == 9 ){                // ��10�У�������ѡѡ��ֵ
                    rowTemp.setSelectValue(cell.getStringCellValue());
                }else if ( j == 10 ){                // ��11�У��Ĵ�������
                    rowTemp.setRegisterType((int) cell.getNumericCellValue());
                }else if ( j == 11 ){                // ��12�У��Ĵ�����ַ
                    rowTemp.setRegister((int) cell.getNumericCellValue());
                }else if ( j == 12 ){                // ��13�У��Ĵ�������
                    rowTemp.setRegisterLength((int) cell.getNumericCellValue());
                }else if ( j == 13 ){               // ��14�У��������
                    rowTemp.setAccuracy( (float) cell.getNumericCellValue() );
                }else if ( j == 14 ){               // ��15�У��Ƿ����ִ��
                    rowTemp.setRunIgnore( (int) cell.getNumericCellValue() );
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

        Cell cell = row.createCell(15); // �±��0��ʼ

        cell.setCellValue(rowBo.getResult());

        // �����º�Ĺ�����������ļ�
        FileOutputStream fileOut = new FileOutputStream(EXCEL_PATH);

        workbook.write(fileOut);

        workbook.close();
        fis.close();
        fileOut.close();
    }

    public void setExcelPath( String path ){
        EXCEL_PATH = path;
    }

    /**
     * ����excel���еĲ������÷�Χ��type=1ʱΪ���ޣ�����Ϊ����
     * */
    public double setCellRange( String range, int type ) {
        // ʹ�ö��ŷָ��ַ���
        String[] parts = range.split("��");
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

    public void setHomeDataExcelPath( String path ){
        HOME_DATA_EXCEL_PATH = path;
    }

    /**
     * Description:  ��ȡhomeData�����ļ�
     * @param
     * @return void
     * @author Graycat. 2023/12/12 16:58
     */
    public List<SingleReadDataBo> readExcelForHomeData() throws IOException {
        // ����һ�� FileInputStream ����ָ�� Excel �ļ�
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(HOME_DATA_EXCEL_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("�ļ�·��Ϊ�գ������.");
            throw new RuntimeException(e);
        }

        // ����һ�� Workbook ����
        Workbook workbook = new XSSFWorkbook(inputStream);

        // ��ȡ��һ��������
        Sheet sheet = workbook.getSheetAt(0);

        List<SingleReadDataBo> result = new ArrayList<>();

        // �����������е�������, ��һ��Ϊ��ͷ���������ӵڶ��п�ʼ
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // ��ȡһ��
            Row row = sheet.getRow(i);
            // ��¼һ��
            SingleReadDataBo rowTemp = new SingleReadDataBo();
            // ��¼����·��
            List<String> funPath = new ArrayList<>();

            // �������е�������
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                // ��ȡһ�У�һ��
                Cell cell = row.getCell(j);
                // ���������ж����Ͳ���ֵ
                switch (j){
                    case 0:
                        rowTemp.setTestSN((int) cell.getNumericCellValue());
                        break;
                    case 1:
                        rowTemp.setName(cell.getStringCellValue());
                        break;
                    case 2:
                        rowTemp.setRegisterType((int) cell.getNumericCellValue());
                        break;
                    case 3:
                        rowTemp.setRegister((int) cell.getNumericCellValue());
                        break;
                    case 4:
                        rowTemp.setRegisterLength((int) cell.getNumericCellValue());
                        break;
                    case 5:
                        rowTemp.setAccuracy((float) cell.getNumericCellValue());
                        break;
                    case 6:
                        rowTemp.setElementId(cell.getStringCellValue());
                        break;
                    case 7:
                        rowTemp.setRunIgnore((int) cell.getNumericCellValue());
                        break;
                    default:
                        LoggerLoad.warn("����excel�ļ�����������.");
                        break;
                }
            }
            result.add(rowTemp);
        }
        // �ر� FileInputStream ����
        inputStream.close();
        return result;
    }


}
