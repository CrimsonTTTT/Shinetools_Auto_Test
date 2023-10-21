package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/23 19:56
 * @Descripe
 */
public class ChooseCollectorPage extends BasePage{

    private By menu_btn = By.id("com.growatt.shinetools:id/action_mode_setting");       // 右上角菜单按钮
    private By usbWifi_btn = By.id("com.growatt.shinetools:id/tv_title");
    private By shineWifiX_btn = By.id("com.growatt.shinetools:id/tv_title");
    private By directWifi_btn = By.id("com.growatt.shinetools:id/tv_title");
    private By wifiX2_btn = By.id("com.growatt.shinetools:id/tv_title");
    private By weLink_btn = By.id("com.growatt.shinetools:id/tv_title");

    public ChooseCollectorPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * type从上到下排列：0 = usbWifi, 1 = ShineWifi-S/X, 2 = Direct wifi/Bluetooth, 3 = shineWifi-x2, 4 = weLink;
     * */
    public ChooseProductionPage chooseTool( int type ){
        if ( type == 0 ){
            waitVisibilityWithAll(directWifi_btn).get(0).click();
        }else if( type == 1 ){
            waitVisibilityWithAll(directWifi_btn).get(1).click();
        }else if( type == 2 ){
            waitVisibilityWithAll(directWifi_btn).get(2).click();
        }else if( type == 3 ){
            waitVisibilityWithAll(directWifi_btn).get(3).click();
        }else if( type == 4 ){
            waitVisibilityWithAll(directWifi_btn).get(4).click();
        }else{
            System.out.println("就五个工具，你想选啥！");
        }
        return new ChooseProductionPage(appiumDriver);
    }

    public void getElement( int type ){
        waitVisibilityWithAll(usbWifi_btn).get(2).click();
    }

}
