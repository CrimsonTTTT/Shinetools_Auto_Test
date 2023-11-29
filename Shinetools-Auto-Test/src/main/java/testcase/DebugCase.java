package testcase;

import bo.ExcelBo;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;
import pages.ConfigsPage;
import util.ExcelUtlis;

import java.io.IOException;
import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/23 11:46
 * @Descripe
 */
public class DebugCase extends BaseCase{

    ExcelUtlis excelUtlis = new ExcelUtlis();

    @Test( groups = "debug", expectedExceptions = TimeoutException.class )
    public void test() throws IOException, InterruptedException {

        String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";

        excelUtlis.setExcelPath(excelPath);

        List<ExcelBo> excelRow = excelUtlis.readExcel(excelPath);

        System.out.println(excelRow.toString());

//        no_devices_debug();

        shineTools_first_test();

//        loginAsCustomerTest();

        // 遍历设置项
        for ( ExcelBo item: excelRow ){
            // 对excel的每一个设置项进行判断并处理
            testConfigItem(item);
        }

    }

    @Test(description = "SPH10k运维账号登录",groups = "Shinetools")
    public void shineTools_first_test() throws InterruptedException {
        // 直连wifi，
        loginPage.loginAsEngineer("grt42110","123456","中国大陆")
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
        Thread.sleep(1000*25);
//        deviceHomePage.intoConfig("快速设置");
    }

    @Test(description = "sph10k终端账号登录")
    public void loginAsCustomerTest() throws InterruptedException {
        loginPage.loginAsCustomer()
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
    }

    @Test(description = "usb_wifi连接设备进入设备首页")
    public void no_devices_debug(  ) throws InterruptedException {
        // usb_wifi链接进入设备页面
        loginPage.loginAsCustomer()
                .chooseTool(0)
                .connectUSBWifi()
                .skipScanCode()
                .chooseProductionType_USB("testName");

    }

    /**
     *  测试基本步骤：
     *      1.前往设置项页面 2.测试方法 3. 返回设备首页
     *      注意：每次进入设置之前从设备首页开始，设置完成之后返回到设置首页
     * */
    public void testConfigItem( ExcelBo excelRow ) throws IOException, InterruptedException {
        if ( excelRow.getType().equals("dir") ){
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // dir项的处理方式.....
            // 判断是否存在该设置项。。。。待实现

            configsPage.backToHome(excelRow.getPath());
            excelRow.setResult("文件夹，略过");
            excelUtlis.writeExcelRowResult(excelRow);
        }else if( excelRow.getType().equals("choose") ){
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // 单选类型的设置项测试方法....
            configsPage.testSelectConfig(excelRow);

            configsPage.backToHome(excelRow.getPath());

        }else if( excelRow.getType().equals("switch") ){
            // 开关类型的设置项测试方法.....

        }else if( excelRow.getType().equals("input") ){
            // 输入数值类型的设置项测试方法.....

        }else{
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // 其他设置项类型的处理方法

            configsPage.backToHome(excelRow.getPath());
            excelRow.setResult("等完善后测试");
            excelUtlis.writeExcelRowResult(excelRow);
        }
        excelUtlis.writeExcelRowResult(excelRow);
    }


    @Test
    public void debug() throws IOException {

        String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";

        excelUtlis.setExcelPath(excelPath);

        List<ExcelBo> excelRow = excelUtlis.readExcel(excelPath);

        excelRow.get(0).setResult("testestestest");

        excelUtlis.writeExcelRowResult(excelRow.get(0));

    }

}
