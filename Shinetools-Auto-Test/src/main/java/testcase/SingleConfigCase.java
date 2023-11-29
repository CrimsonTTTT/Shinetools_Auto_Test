package testcase;

import bo.ExcelBo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelUtlis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/20 14:44
 * @Descripe 使用TestNG的数据驱动，解耦单个设置项用例的执行 ------- 实际上前面的用例执行失败后会造成找不到后面的元素。需要想办法处理
 */
public class SingleConfigCase extends BaseCase{

    private ExcelUtlis excelUtlis = new ExcelUtlis();
    private String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";

    // 使用testng 的依赖注解。尝试下 或者使用static计数
    @Test
    public void connetDeviceTest() throws InterruptedException {
        // 直连wifi
        loginPage.loginAsEngineer("grt42110","pwd123456","中国大陆")
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
        Thread.sleep(1000*25);
    }

    @Test(dataProvider = "testExcelData", groups = "singleConfig", dependsOnMethods = "connetDeviceTest")
    public void singleConfigCaseTest( ExcelBo bo ) throws InterruptedException, IOException {
//        System.out.println( "数据驱动获取的excel：" + bo.toString() );

        deviceHomePage.intoConfig(bo.getPath().get(0));

        configsPage.toConfigPage(bo.getPath());

        switch ( bo.getType() ){
            case "dir":
                System.out.println("这是文件夹的测试方法");
                configsPage.backToHome(bo.getPath());
                bo.setResult("功能未完善，后续优化");
                break;

            case "choose":
                System.out.println("这是单选型设置项的测试方法");
                configsPage.testSelectConfig(bo);
                configsPage.backToHome(bo.getPath());
                System.out.println("单选项测试结果：" + bo );
                break;

            case "input":
                System.out.println("输入型设置项的测试方法");
                configsPage.
                        testInputConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "switch":
                System.out.println("开关设置项的测试方法");
                configsPage.backToHome(bo.getPath());
                break;

            case "other":
                System.out.println("这是特殊设置项的测试方法");
                configsPage.backToHome(bo.getPath());
                bo.setResult("功能未完善，后续优化");
                break;

            default:
                System.out.println("不知名设置项的....");
                configsPage.backToHome(bo.getPath());
                bo.setResult("设置项类型有误");
                break;
        }
        // 结果保存excel
        excelUtlis.writeExcelRowResult(bo);
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


}
