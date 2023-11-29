package pages;

import java.time.Duration;
import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * @Author GrayCat.
 * @date 2022/01/12 10:36
 */
public class BasePage {
    public AppiumDriver appiumDriver;
    public WebDriverWait appiumDriverWait;


    public BasePage(AppiumDriver driver){
        this.appiumDriver = driver;
        appiumDriverWait = new WebDriverWait(driver, 10 );
    }

    //Wait
    public WebElement waitVisibility(By by) {
        return appiumDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public List<WebElement> waitVisibilityWithAll(By by) {
        return appiumDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    //Click Method
    public void click(By by) throws InterruptedException {
        try{
            waitVisibility(by).click();
        }catch (TimeoutException e){
            // 可以优化，判断加载框的情况
            System.out.println("点击时找不到元素.等待时间+5s后再次点击看看情况.");
            Thread.sleep(5*1000);
            waitVisibility(by).click();
        }
    }

    //Write Text
    public void writeText(By by, String text) {
        waitVisibility(by).sendKeys(text);
    }

    //Read Text
    public String readText(By by) {
        return waitVisibility(by).getText();
    }

    public String readToastText(By by){
        WebDriverWait toastDriverWait = new WebDriverWait(appiumDriver, 10,50 );
        WebElement element = toastDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element.getText();
    }

    // 读取控件的属性值
    public String readAttribute(By by,String attributeName){
        return waitVisibility(by).getAttribute(attributeName);
    }

    // 上下滑动一定距离
    public void swipeDownHalf(  ){
        Dimension size = appiumDriver.manage().window().getSize();
        int startx = size.width / 2;
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.2);
        Duration duration = Duration.ofSeconds(1);
        TouchAction swipe = new TouchAction(appiumDriver).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(startx, endy)).release();
        swipe.perform();
//        System.out.println("屏幕大小："+size.width+"  "+size.height);
    }

    /** 返回操作 */
    public void back( By by ){
//        if ( waitVisibility(by) != null ){
//            // 如果元素可见
//            appiumDriver.navigate().back();
//        }else{
//            // 当前页面处于不可返回状态
//            System.out.println("当前页面处于不可返回状态");
//        }
        appiumDriver.navigate().back();
    }



}
