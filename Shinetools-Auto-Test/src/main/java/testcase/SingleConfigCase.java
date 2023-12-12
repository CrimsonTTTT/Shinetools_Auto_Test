package testcase;

import bo.ExcelBo;
import config.LoggerLoad;
import config.RetryHandler;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DeviceHomePage;
import pages.settings.AdvancedSettingPage;
import util.ExcelUtlis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/20 14:44
 * @Descripe ʹ��TestNG�����������������������������ִ�� ------- ʵ����ǰ�������ִ��ʧ�ܺ������Ҳ��������Ԫ�ء�
 *          ���������ʹ��testngʧ�����Ի��ƣ�ʧ�ܺ�Ͽ�driver���������豸���к�������
 */
@Epic("ShineTools")   // ��������ĸ���Ŀ
@Feature("������")     // ��������ĸ�ģ��
public class SingleConfigCase extends BaseCase{

    private ExcelUtlis excelUtlis = new ExcelUtlis();
//    private String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";
    private String excelPath = "C:\\Users\\grt-linweijia\\Desktop\\Auto_SPH10000TL-HU - ����.xlsx";


    // ʹ��testng ������ע�⡣
    @Test
    public static void connectDeviceTest() throws InterruptedException {
        // ֱ��wifi
        loginPage.loginAsEngineer("grt42110","pwd123456","�й���½")
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
        Thread.sleep(1000*25);
    }

    @Test(dataProvider = "testExcelData", groups = "singleConfig", dependsOnMethods = "connectDeviceTest", retryAnalyzer = RetryHandler.class)
    public void singleConfigCaseTest( ExcelBo bo ) throws InterruptedException, IOException {
        if (bo.getRunIgnore() == 1){
            bo.setResult("Ignore");
            excelUtlis.writeExcelRowResult(bo);
            throw new SkipException("���TestNG���Ϊ��ִ��.");
        }

        LoggerLoad.info("��ǰִ�еĲ����" + bo.toString());

        DeviceHomePage deviceHomePage1 = new DeviceHomePage(appiumDriver);

        deviceHomePage1.intoConfig(bo.getPath().get(0));

        configsPage.toConfigPage(bo.getPath());

        switch ( bo.getType() ){
            case "dir":
                LoggerLoad.info("�����ļ��еĲ��Է���");
                configsPage.backToHome(bo.getPath());
                bo.setResult("����δ����");
                break;

            case "choose":
                LoggerLoad.info("==============���ǵ�ѡ��������Ĳ��Է���,׼��ִ��==================");
                configsPage.testSelectConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "input":
                LoggerLoad.info("===============������������Ĳ��Է���,׼��ִ��=======================");
                configsPage.testInputConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "switch":
                LoggerLoad.info("================������������Ĳ��Է���,׼��ִ��======================");
                configsPage.testSwitchConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "text":
                LoggerLoad.info("=================�ı�������Ĳ��Է���,׼��ִ��======================");
                configsPage.testTextConfig(bo);
                break;

            case "other":
                LoggerLoad.info("��������������Ĳ��Է���");
                configsPage.backToHome(bo.getPath());
                bo.setResult("����δ����");
                break;

            default:
                LoggerLoad.info("��֪���������....");
                configsPage.backToHome(bo.getPath());
                bo.setResult("��������������");
                break;
        }
        // �������excel
        excelUtlis.writeExcelRowResult(bo);
        LoggerLoad.info("========================����������ָ���===============================");
    }

    /**
     * Description:  ���������ṩ
     * @param
     * @author Graycat. 2023/11/20 15:01
     */
    @DataProvider(name = "testExcelData")
    public Object[][] testDataWithVague() throws IOException {

        excelUtlis.setExcelPath(excelPath);

        List<ExcelBo> excelRowData = excelUtlis.readExcel(excelPath);

        Object[][] result = new Object[excelRowData.size()][];

        for ( int i=0; i<excelRowData.size(); i++ ){
            result[i] = new Object[]{ excelRowData.get(i) };
        }
        return result;
    }

    @Test(enabled = false)
    @io.qameta.allure.Story("����Ϊ��ִ��")
    @io.qameta.allure.Description("���@Description")
    public void debugTest() throws InterruptedException {
        connectDeviceTest();
        deviceHomePage.intoConfig("�߼�����");
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        Map result = advancedSettingPage.readMultiRegisterValue(3,10000,1);
        System.out.println(result.get(10000) + " ss:" + result.get(10001));
        System.out.println(result);
    }


}
