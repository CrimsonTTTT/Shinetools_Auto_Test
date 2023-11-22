package testcase;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Graycat.
 * @CreateTime 2023/10/21 16:20
 * @Descripe
 */
public class XXXCase {
    String excelPath = "D:\\TestCode\\ExcelTest.xlsx";

    /**
     * Description:  调试用，用来测试部分代码；本地代码分支修改
     * @param
     * @return void
     * @author Graycat. 2023/10/24 14:58
     */
    @Test
    public void testSomeFun() throws IOException {
       String targetStr = "108--2 109--3 110--4";
       Map<Integer,Integer> result = new HashMap<>();

    }

    @Test
    public void getRegisterResult( ) throws IOException {
        System.out.println("dddd");
    }
}
