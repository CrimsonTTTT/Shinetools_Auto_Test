package testcase;

import bo.ExcelBo;
import bo.SingleReadDataBo;
import config.LoggerLoad;
import config.RetryHandler;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelUtlis;

import java.io.IOException;
import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/12/12 17:25
 * @Descripe
 */
@Epic("ShineTools")   // 标记属于哪个项目
@Feature("首页数据点")     // 标记属于哪个模块
public class HomeDataCase extends BaseCase{

    private ExcelUtlis excelUtlis = new ExcelUtlis();
    private String excelPath = "D:\\TestCode\\Auto_HomeData_SPH10000TL-HU.xlsx"; ;

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

    @DataProvider(name = "testExcelHomeData")
    public Object[][] testDataWithVague() throws IOException {

        excelUtlis.setHomeDataExcelPath(excelPath);
        List<SingleReadDataBo> excelRowData = excelUtlis.readExcelForHomeData();

        Object[][] result = new Object[excelRowData.size()][];

        for ( int i=0; i<excelRowData.size(); i++ ){
            result[i] = new Object[]{ excelRowData.get(i) };
        }
        return result;
    }

    @Test(dataProvider = "testExcelHomeData", dependsOnMethods = "connectDeviceTest", retryAnalyzer = RetryHandler.class)
    @io.qameta.allure.Story("首页数据点测试")
    public void deviceHomeDataTest( SingleReadDataBo bo ) throws InterruptedException {
        LoggerLoad.debug("debug用：" + bo);
        testHomeData();
    }


    private void testHomeData() {


    }




}
