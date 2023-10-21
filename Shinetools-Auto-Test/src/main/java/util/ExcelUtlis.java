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
     *  读取excel行到实体类BO
     */
    public List<ExcelBo> readExcel(String path ) throws IOException {
        // 创建一个 FileInputStream 对象，指向 Excel 文件
        FileInputStream inputStream = new FileInputStream(path);

        // 创建一个 Workbook 对象
        Workbook workbook = new XSSFWorkbook(inputStream);

        // 获取第一个工作表
        Sheet sheet = workbook.getSheetAt(0);

        List<ExcelBo> result = new ArrayList<>();

        // 遍历工作表中的所有行, 第一行为表头，跳过，从第二行开始
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 获取一行
            Row row = sheet.getRow(i);
            // 记录一行
            ExcelBo rowTemp = new ExcelBo();
            // 记录功能路径
            List<String> funPath = new ArrayList<>();

            // 遍历行中的所有列
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                // 获取一列
                Cell cell = row.getCell(j);

                // 根据列数判断类型并赋值
                if( j == 0 ){                       // 第1列，功能编号
                    rowTemp.setTestSN(cell.getNumericCellValue());
                }else if ( j >= 1 && j <= 6){       // 第2列到第7列，功能的路径
                    // 记录第2到7列的路径
                    funPath.add(cell.getStringCellValue());
                }else if( j == 7 ){                 // 第8列，参数类型
                    rowTemp.setType(cell.getStringCellValue());
                }else if( j == 8 ){                 // 第9列，参数范围
                    // 判断是否为空，‘/’代表为空
                    if ( cell.getStringCellValue().equals("/") ){
                        rowTemp.setRangeHigh(0);
                        rowTemp.setRangeLow(0);
                    }else {
                        double high = setCellRange(cell.getStringCellValue(), 1);
                        double low = setCellRange(cell.getStringCellValue(), 2);
                        rowTemp.setRangeHigh(high);
                        rowTemp.setRangeLow(low);
                    }
                }else if ( j == 9 ){                // 第10列，参数可选选项值
                    rowTemp.setSelectValue(cell.getStringCellValue());
                }else if ( j == 10 ){                // 第11列，寄存器类型
                    rowTemp.setRegisterType((int) cell.getNumericCellValue());
                }else if ( j == 11 ){                // 第12列，寄存器地址
                    rowTemp.setRegister((int) cell.getNumericCellValue());
                }else if ( j == 12 ){                // 第13列，寄存器长度
                    rowTemp.setRegisterLength((int) cell.getNumericCellValue());
                }
                else {
                    rowTemp.setRemark("Excel数据有问题，此行存在"+j+"列");
                }
            }
            rowTemp.setPath(funPath);
            result.add(rowTemp);
        }

        // 关闭 FileInputStream 对象
        inputStream.close();

        return result;
    }


    /**
     * Description:  向已有excel写入行，只写入结果
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

        Cell cell = row.createCell(13); // 下标从0开始

        cell.setCellValue(rowBo.getResult());

        // 将更新后的工作簿保存回文件
        FileOutputStream fileOut = new FileOutputStream(EXCEL_PATH);

        workbook.write(fileOut);

        workbook.close();
        fis.close();
        fileOut.close();
    }

    /**
     *  获取当前列表数据
     * */
    public void getConfigDataList(){

    }

    public void setExcelPath( String path ){
        EXCEL_PATH = path;
    }

    /**
     * 解析范围，type=1时为下限，其他为上限
     * */
    public double setCellRange( String range, int type ) {
        // 使用逗号分割字符串
        String[] parts = range.split(",");
        double value1 = 0, value2 = 0;
        if (parts.length == 2) {
            try {
                // 将分割后的字符串转换为 double
                value1 = Double.parseDouble(parts[0]);
                value2 = Double.parseDouble(parts[1]);

            } catch (NumberFormatException e) {
                System.err.println("无法解析为 double 值。");
            }
        } else {
            System.err.println("输入字符串格式不正确。");
        }
        if (type == 1) {
            return value1;
        }else return value2;
    }


}
