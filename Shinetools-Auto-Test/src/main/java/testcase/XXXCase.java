package testcase;

import bo.ExcelBo;
import config.LoggerLoad;
import config.RetryHandler;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelUtlis;
import util.MathUtils;
import util.StrUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
     * Description:  调试用，用来测试部分代码；远程代码修改；
     *          这是在本地的main分支修改的....22222
     * @param
     * @return void
     * @author Graycat. 2023/10/24 14:58
     */
    @Test(retryAnalyzer = RetryHandler.class)
    public void testSomeFun() throws IOException, InterruptedException {
        float testValue = 122f;

        ExcelUtlis utlis = new ExcelUtlis();
        utlis.setExcelPath(excelPath);
        utlis.readExcel(excelPath);


        System.out.println("result " + MathUtils.countDecimalPlaces(testValue));
    }

    @Test
    public void getRegisterResult( ) throws IOException {
        String str = "范围值:52.1~59.2V";
        String str1 = "范围值:0~222V";
        String str2 = "dfd:-20.1~-1.0V";

        // 定义正则表达式
        String regex = "(-?\\d+(?:\\.\\d*)?)~(-?\\d+(?:\\.\\d*)?)";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(str2);
        // 查找匹配
        if (matcher.find()) {
            // 获取第一个匹配组的值（0 的值）
            String firstValue = matcher.group(1);
            // 获取第二个匹配组的值（2000 的值）
            String secondValue = matcher.group(2);
            // 输出结果
            System.out.println("firstValue: " + firstValue);
            System.out.println("secondValue: " + secondValue);
        }
    }


    @Test(dataProvider = "myDataProvider")
    public void myTestMethod(String data) {
        // Your test logic here
        System.out.println("Test data: " + data);
        if ("fail".equals(data)) {
            // 模拟测试失败的情况
            throw new RuntimeException("Test failed for data: " + data);
        }
    }


    @DataProvider(name = "myDataProvider")
    public  Object[][] provideData() {
        return new Object[][]{
                {"data1"},
                {"data2"},
                {"fail"},  // 这里模拟一个导致失败的数据
                {"data3"},
                // 可以添加更多的数据
        };
    }

    @Test
    public  void test111(  ) {
        // 指定范围和小数位数
        double minValue = 5.0;
        double maxValue = 10.0;
        int decimalPlaces = 2;

        // 生成随机数
        double randomValue = generateRandomNumber(minValue, maxValue);

        // 格式化随机数
        String formattedRandomValue = formatDecimal(9.87654321, decimalPlaces);

        // 输出结果
        System.out.println("Random number within range: " + formattedRandomValue);
    }

    private static double generateRandomNumber(double minValue, double maxValue ) {
        Random rand = new Random();
        return minValue + (maxValue - minValue) * rand.nextDouble();
    }

    private static String formatDecimal(double value, int decimalPlaces) {
        String pattern = "0.";
        for (int i = 0; i < decimalPlaces; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    @Test
    public void debugLog4j2(){
        LoggerLoad.info("dddddd");
        LoggerLoad.error("dafd");
        LoggerLoad.warn("2342432423");
        LoggerLoad.debug("this is debug level.");
    }

    @Test
    public void debugChangeType(){
        String a = "461.0";
        String b = "46.1";
        float jingdu = 0.1f;

        double d_a = Double.parseDouble(a);
        double d_b = Double.parseDouble(b);

        System.out.println(d_b/jingdu + "  "+ d_a);
        System.out.println( MathUtils.compareDoubleEquals(d_a,d_b/jingdu));

    }

    @Test
    public void debugNumsParseStr(){
        // 8位二进制码
        String binaryCode = "01010011";

        // 将8位二进制码转换为字符
        char resultChar = binaryCodeToChar(binaryCode);

        // 打印结果
        System.out.println("Binary Code: " + binaryCode);
        System.out.println("Corresponding Character: " + resultChar);

        System.out.println(StrUtils.numbersParseToStr(21323));
        StrUtils.numbersParseToStr(21323);
        StrUtils.numbersParseToStr(12336);
        StrUtils.numbersParseToStr(12334);
        StrUtils.numbersParseToStr(11568);
        StrUtils.numbersParseToStr(12408);

    }

    // 将8位二进制码转换为字符
    private static char binaryCodeToChar(String binaryCode) {
        // 将二进制字符串转换为整数
        int intValue = Integer.parseInt(binaryCode, 2);

        // 将整数转换为字符
        return (char) intValue;
    }


}
