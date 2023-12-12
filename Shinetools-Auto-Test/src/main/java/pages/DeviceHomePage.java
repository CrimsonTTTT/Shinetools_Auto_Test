package pages;

import bo.ExcelBo;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/26 11:02
 * @Descripe
 */
public class DeviceHomePage extends BasePage{

    By setting_list = By.id("com.growatt.shinetools:id/rv_setting");
    By config_list = By.id("com.growatt.shinetools:id/tv_name");

    public DeviceHomePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Description:  前往某一设置项
     * @param configName java.lang.String
     * @return pages.ConfigsPage
     * @author Graycat. 2023/9/28 15:16
     */
    public ConfigsPage intoConfig( String configName ) throws InterruptedException {
        swipeDownHalf();
        List<WebElement> configList = waitVisibilityWithAll(config_list);

        for ( WebElement item: configList ){
            if (item.getText().equals(configName)){
                item.click();
                break;
            }
        }
        return new ConfigsPage(appiumDriver);
    }


    public String readSingleDataText(By by){
        return readText(by);
    }


}
