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
 * @Descripe ʹ��TestNG�����������������������������ִ�� ------- ʵ����ǰ�������ִ��ʧ�ܺ������Ҳ��������Ԫ�ء���Ҫ��취����
 */
public class SingleConfigCase extends BaseCase{

    private ExcelUtlis excelUtlis = new ExcelUtlis();
    private String excelPath = "D:\\TestCode\\Auto_SPH10000TL-HU.xlsx";

    // ʹ��testng ������ע�⡣������ ����ʹ��static����
    @Test
    public void connetDeviceTest() throws InterruptedException {
        // ֱ��wifi
        loginPage.loginAsEngineer("grt42110","pwd123456","�й���½")
                .chooseTool(2)
                .chooseProductionType(2)
                .chooseInputSNType(1)
                .writeCode("QZM10SKLW1")
                .confirm();
        Thread.sleep(1000*25);
    }

    @Test(dataProvider = "testExcelData", groups = "singleConfig", dependsOnMethods = "connetDeviceTest")
    public void singleConfigCaseTest( ExcelBo bo ) throws InterruptedException, IOException {
//        System.out.println( "����������ȡ��excel��" + bo.toString() );

        deviceHomePage.intoConfig(bo.getPath().get(0));

        configsPage.toConfigPage(bo.getPath());

        switch ( bo.getType() ){
            case "dir":
                System.out.println("�����ļ��еĲ��Է���");
                configsPage.backToHome(bo.getPath());
                bo.setResult("����δ���ƣ������Ż�");
                break;

            case "choose":
                System.out.println("���ǵ�ѡ��������Ĳ��Է���");
                configsPage.testSelectConfig(bo);
                configsPage.backToHome(bo.getPath());
                System.out.println("��ѡ����Խ����" + bo );
                break;

            case "input":
                System.out.println("������������Ĳ��Է���");
                configsPage.
                        testInputConfig(bo);
                configsPage.backToHome(bo.getPath());
                break;

            case "switch":
                System.out.println("����������Ĳ��Է���");
                configsPage.backToHome(bo.getPath());
                break;

            case "other":
                System.out.println("��������������Ĳ��Է���");
                configsPage.backToHome(bo.getPath());
                bo.setResult("����δ���ƣ������Ż�");
                break;

            default:
                System.out.println("��֪���������....");
                configsPage.backToHome(bo.getPath());
                bo.setResult("��������������");
                break;
        }
        // �������excel
        excelUtlis.writeExcelRowResult(bo);
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


}
