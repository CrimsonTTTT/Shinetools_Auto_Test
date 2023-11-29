package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/26 9:29
 * @Descripe
 */
public class ScanCodePage extends BasePage{

    By scan_btn = By.id("com.growatt.shinetools:id/iv_sd");
    By manual_btn = By.id("com.growatt.shinetools:id/iv_sd");
    By inputSN_input = By.id("com.growatt.shinetools:id/et_input_sn");
    By toScan_btn = By.id("com.growatt.shinetools:id/ll_scan");
    By confirm_btn = By.id("com.growatt.shinetools:id/btnNext");
    By skip_btn = By.id("com.growatt.shinetools:id/right_action");

    public ScanCodePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * ѡ��������豸���ͣ�0��ɨ�룻1���ֶ�����
     * */
    public ScanCodePage chooseInputSNType( int type ) throws InterruptedException {
        if( type == 0 ){
            click(scan_btn);
        }else if( type == 1 ){
            click(manual_btn);
        }else {
            System.err.println("���õ���������");
        }
        return new ScanCodePage(appiumDriver);
    }

    public ScanCodePage writeCode( String sn ){
        writeText(inputSN_input,sn);
        return this;
    }

    public void confirm() throws InterruptedException {
        click(confirm_btn);
    }

    public ChooseProductionPage skipScanCode(  ){
        try{
            click(skip_btn);
        }
        catch(TimeoutException | InterruptedException e){
            System.out.println("ɨ��ҳ���������ť��ʱ���ж�Ϊ������");
        }
        return new ChooseProductionPage(appiumDriver);
    }
}
