package testcase;

import bo.ExcelBo;
import bo.SingleReadDataBo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import config.LoggerLoad;
import config.RetryHandler;
import io.qameta.allure.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelUtlis;
import util.MathUtils;
import util.StrUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
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
     * Description:  �����ã��������Բ��ִ��룻Զ�̴����޸ģ�
     *          �����ڱ��ص�main��֧�޸ĵ�....22222
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
        String str = "��Χֵ:52.1~59.2V";
        String str1 = "��Χֵ:0~222V";
        String str2 = "dfd:-20.1~-1.0V";

        // ����������ʽ
        String regex = "(-?\\d+(?:\\.\\d*)?)~(-?\\d+(?:\\.\\d*)?)";
        // ����������ʽ
        Pattern pattern = Pattern.compile(regex);
        // ���� Matcher ����
        Matcher matcher = pattern.matcher(str2);
        // ����ƥ��
        if (matcher.find()) {
            // ��ȡ��һ��ƥ�����ֵ��0 ��ֵ��
            String firstValue = matcher.group(1);
            // ��ȡ�ڶ���ƥ�����ֵ��2000 ��ֵ��
            String secondValue = matcher.group(2);
            // ������
            System.out.println("firstValue: " + firstValue);
            System.out.println("secondValue: " + secondValue);
        }
    }


    @Test(dataProvider = "myDataProvider")
    public void myTestMethod(String data) {
        // Your test logic here
        System.out.println("Test data: " + data);
        if ("fail".equals(data)) {
            // ģ�����ʧ�ܵ����
            throw new RuntimeException("Test failed for data: " + data);
        }
    }


    @DataProvider(name = "myDataProvider")
    public  Object[][] provideData() {
        return new Object[][]{
                {"data1"},
                {"data2"},
                {"fail"},  // ����ģ��һ������ʧ�ܵ�����
                {"data3"},
                // ������Ӹ��������
        };
    }

    @Test
    public  void test111(  ) {
        // ָ����Χ��С��λ��
        double minValue = 5.0;
        double maxValue = 10.0;
        int decimalPlaces = 2;

        // ���������
        double randomValue = generateRandomNumber(minValue, maxValue);

        // ��ʽ�������
        String formattedRandomValue = formatDecimal(9.87654321, decimalPlaces);

        // ������
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
    @Epic("�����@Epic")
    @Feature("testNG+allure����")                      // xxx
    @Description("����ע��@Description��debug��")       // ������������
    @Story("���Բ���ע��@Story")                        // ��ע��Ϣ
    @Severity(SeverityLevel.BLOCKER)                 // ���ȱ�ݵȼ�
    @Step                                            // ����
    public void debugNumsParseStr(){
        // 8λ��������
        String binaryCode = "01010011";

        // ��8λ��������ת��Ϊ�ַ�
        char resultChar = binaryCodeToChar(binaryCode);

        // ��ӡ���
        System.out.println("Binary Code: " + binaryCode);
        System.out.println("Corresponding Character: " + resultChar);

        System.out.println(StrUtils.numbersParseToStr(21323));
        StrUtils.numbersParseToStr(21323);
        StrUtils.numbersParseToStr(12336);
        StrUtils.numbersParseToStr(12334);
        StrUtils.numbersParseToStr(11568);
        StrUtils.numbersParseToStr(12408);

    }

    // ��8λ��������ת��Ϊ�ַ�
    private static char binaryCodeToChar(String binaryCode) {
        // ���������ַ���ת��Ϊ����
        int intValue = Integer.parseInt(binaryCode, 2);

        // ������ת��Ϊ�ַ�
        return (char) intValue;
    }

    @Description("Descriptionע�⣺��֤��ҳ����1")
    @Story("��������")
    @Test
    public void debugMap(){

        String selectValueStr = "{'dd':1,   '�� ��':2}";
        LinkedHashMap<String,Integer> expectMap = new LinkedHashMap();
        expectMap = JSON.parseObject(selectValueStr, new TypeReference<LinkedHashMap<String, Integer>>(){});
        System.out.println(expectMap);
        throw new SkipException("���Ϊ��ִ��.");
    }

    @Test
    public void debugExcelData() throws IOException {
        String path = "D:\\TestCode\\Auto_HomeData_SPH10000TL-HU.xlsx";
        ExcelUtlis excelUtlis = new ExcelUtlis();
        excelUtlis.setHomeDataExcelPath(path);
        List<SingleReadDataBo> boList = excelUtlis.readExcelForHomeData();
        System.out.println(boList);
    }
}
