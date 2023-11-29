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

        // ����������
        for ( ExcelBo item: excelRow ){
            // ��excel��ÿһ������������жϲ�����
            testConfigItem(item);
        }

    }

    @Test(description = "SPH10k��ά�˺ŵ�¼",groups = "Shinetools")
    public void shineTools_first_test() throws InterruptedException {
        // ֱ��wifi��
        loginPage.loginAsEngineer("grt42110","123456","�й���½")
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
        Thread.sleep(1000*25);
//        deviceHomePage.intoConfig("��������");
    }

    @Test(description = "sph10k�ն��˺ŵ�¼")
    public void loginAsCustomerTest() throws InterruptedException {
        loginPage.loginAsCustomer()
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
    }

    @Test(description = "usb_wifi�����豸�����豸��ҳ")
    public void no_devices_debug(  ) throws InterruptedException {
        // usb_wifi���ӽ����豸ҳ��
        loginPage.loginAsCustomer()
                .chooseTool(0)
                .connectUSBWifi()
                .skipScanCode()
                .chooseProductionType_USB("testName");

    }

    /**
     *  ���Ի������裺
     *      1.ǰ��������ҳ�� 2.���Է��� 3. �����豸��ҳ
     *      ע�⣺ÿ�ν�������֮ǰ���豸��ҳ��ʼ���������֮�󷵻ص�������ҳ
     * */
    public void testConfigItem( ExcelBo excelRow ) throws IOException, InterruptedException {
        if ( excelRow.getType().equals("dir") ){
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // dir��Ĵ���ʽ.....
            // �ж��Ƿ���ڸ��������������ʵ��

            configsPage.backToHome(excelRow.getPath());
            excelRow.setResult("�ļ��У��Թ�");
            excelUtlis.writeExcelRowResult(excelRow);
        }else if( excelRow.getType().equals("choose") ){
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // ��ѡ���͵���������Է���....
            configsPage.testSelectConfig(excelRow);

            configsPage.backToHome(excelRow.getPath());

        }else if( excelRow.getType().equals("switch") ){
            // �������͵���������Է���.....

        }else if( excelRow.getType().equals("input") ){
            // ������ֵ���͵���������Է���.....

        }else{
            deviceHomePage.intoConfig(excelRow.getPath().get(0));
            configsPage.toConfigPage(excelRow.getPath());
            // �������������͵Ĵ�����

            configsPage.backToHome(excelRow.getPath());
            excelRow.setResult("�����ƺ����");
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
