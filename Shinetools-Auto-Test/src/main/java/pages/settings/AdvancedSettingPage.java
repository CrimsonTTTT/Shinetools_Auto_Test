package pages.settings;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.BasePage;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/28 11:24
 * @Descripe
 */
public class AdvancedSettingPage extends BasePage {

    By commandType_input = By.id("com.growatt.shinetools:id/etCommand");
    By address_input = By.id("com.growatt.shinetools:id/etRegisterAddress");
    By lengthData_input = By.id("com.growatt.shinetools:id/etLengthData");
    By confirm_btn = By.id("com.growatt.shinetools:id/btnStart");

    By result_area = By.id("com.growatt.shinetools:id/rvReceiver");
    By top_title = By.id("com.growatt.shinetools:id/toolbar");
    public AdvancedSettingPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Description:  ∂¡»°÷∏¡Ó
     * @param type int
     * @param address int
     * @param lengthOrData int
     * @return void
     * @author Graycat. 2023/9/28 11:32
     */
    public String readRegisterValue( int type, int address, int lengthOrData ){
        writeText(commandType_input, String.valueOf(type));
        writeText(address_input, String.valueOf(address));
        writeText(lengthData_input, String.valueOf(lengthOrData));
        click(confirm_btn);
        String result = readText(result_area);
        return result;
    }

    public void backToDeviceHome(  ){
        back(top_title);
    }

}
