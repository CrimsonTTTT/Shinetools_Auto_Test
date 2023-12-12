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
 * @Descripe 使用TestNG的数据驱动，解耦单个设置项用例的执行 ------- 实际上前面的用例执行失败后会造成找不到后面的元素。
 *          解决方案：使用testng失败重试机制，失败后断开driver，再重连设备进行后续测试
 */
@Epic("ShineTools")   // 标记属于哪个项目
@Feature("设置项")     // 标记属于哪个模块
public class SingleConfigCase extends BaseCase{

    private ExcelUtlis excelUtlis = new ExcelUtlis();
//    private String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";
    private String excelPath = "C:\\Users\\grt-linweijia\\Desktop\\Auto_SPH10000TL-HU - 副本.xlsx";


    // 使用testng 的依赖注解。
    @Test
    public static void connectDeviceTest() throws InterruptedException {
        // 直连wifi
        loginPage.loginAsEngineer("grt42110","pwd123456","中国大陆")
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
            throw new SkipException("标记TestNG结果为不执行.");
        }

        LoggerLoad.info("当前执行的测试项：" + bo.toString());

        DeviceHomePage deviceHomePage1 = new DeviceHomePage(appiumDriver);

        deviceHomePage1.intoConfig(bo.getPath().get(0));

        configsPage.toConfigPage(bo.getPath());

        switch ( bo.getType() ){
            case "dir":
                LoggerLoad.info("这是文件夹的测试方法");
                configsPage.backToHome(bo.getPath());
                bo.setResult("功能未完善");
                break;

            case "choose":
                LoggerLoad.info("==============这是单选型设置项的测试方法,准备执行==================");
                configsPage.testSelectConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "input":
                LoggerLoad.info("===============输入型设置项的测试方法,准备执行=======================");
                configsPage.testInputConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "switch":
                LoggerLoad.info("================开关型设置项的测试方法,准备执行======================");
                configsPage.testSwitchConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "text":
                LoggerLoad.info("=================文本设置项的测试方法,准备执行======================");
                configsPage.testTextConfig(bo);
                break;

            case "other":
                LoggerLoad.info("这是特殊设置项的测试方法");
                configsPage.backToHome(bo.getPath());
                bo.setResult("功能未完善");
                break;

            default:
                LoggerLoad.info("不知名设置项的....");
                configsPage.backToHome(bo.getPath());
                bo.setResult("设置项类型有误");
                break;
        }
        // 结果保存excel
        excelUtlis.writeExcelRowResult(bo);
        LoggerLoad.info("========================设置项结束分割线===============================");
    }

    /**
     * Description:  测试数据提供
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
    @io.qameta.allure.Story("设置为不执行")
    @io.qameta.allure.Description("这个@Description")
    public void debugTest() throws InterruptedException {
        connectDeviceTest();
        deviceHomePage.intoConfig("高级设置");
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        Map result = advancedSettingPage.readMultiRegisterValue(3,10000,1);
        System.out.println(result.get(10000) + " ss:" + result.get(10001));
        System.out.println(result);
    }


}
