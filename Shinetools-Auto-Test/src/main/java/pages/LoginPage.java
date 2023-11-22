package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/23 16:23
 * @Descripe
 */
public class LoginPage extends BasePage{

    private By customerPassword = By.id("com.growatt.shinetools:id/et_end_password");
    private By engineerUsernameInput = By.id("com.growatt.shinetools:id/et_username");
    private By engineerPasswordInput = By.id("com.growatt.shinetools:id/et_password");
    private By loginBtn = By.id("com.growatt.shinetools:id/btn_login");
    private By role_customer = By.id("com.growatt.shinetools:id/radio_end");
    private By role_engineer = By.id("com.growatt.shinetools:id/radio_maintain");
    private By agreementChoose = By.id("com.growatt.shinetools:id/cb_agreement");
    private By welcomeConfirm_btn = By.id("com.growatt.shinetools:id/btn_login");
    private By welcome_text = By.id("com.growatt.shinetools:id/tv_text_title");

    private By areaList = By.className("android.widget.TextView");

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public ChooseCollectorPage loginAsCustomer(  ){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 使用 DateTimeFormatter 格式化日期，包括两位数的月份
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        click(role_customer);

        // 终端用户密码：oss+当天年月日
        String nowDate = "oss"+ formattedDate;
        writeText(customerPassword,nowDate);
        click(loginBtn);

        // 欢迎页面 --- 待补充
        /*if( true ){
            click(welcomeConfirm_btn);
        }*/

        return new ChooseCollectorPage(appiumDriver);
    }

    public ChooseCollectorPage loginAsEngineer( String username, String password, String serverArea ){
        click(role_engineer);
        writeText(engineerUsernameInput,username);
        writeText(engineerPasswordInput,password);
        click(loginBtn);
        chooseServerArea(serverArea);
        return new ChooseCollectorPage(appiumDriver);
    }

    public void chooseServerArea( String area ){
        List<WebElement> list = waitVisibilityWithAll(areaList);
        for (WebElement item : list){
            if (item.getText().equals(area)){
                item.click();
                break;
            }
        }
    }

    // 取消按钮，登录后那个下载固件包失败弹窗的取消
    public void cancelDownLoad(){
        chooseServerArea("取消");
    }

}
