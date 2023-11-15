package testcase;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.ConfigsPage;
import pages.DeviceHomePage;
import pages.LoginPage;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Author GrayCat.
 * @date 2022/01/12 11:09
 */
public class BaseCase {
    public AppiumDriver appiumDriver;

    public LoginPage loginPage;

    public DeviceHomePage deviceHomePage;

    public ConfigsPage configsPage;

    public AppiumDriver getDriver() {
        return appiumDriver;
    }

    @BeforeClass(groups = "base")
    public void classLevelSetup() throws MalformedURLException {
        System.out.println("ִ��beforeclass");
        //���DesiredCapabilities�����
        DesiredCapabilities des2 = new DesiredCapabilities();
        // ָ������ƽ̨
        des2.setCapability("sessionOverride", true);
        des2.setCapability("platformName", "Android");
        des2.setCapability("platformVersion", "12");
        //ָ�����Ի�id,ͨ��adb����(adb devices)��ȡ
        des2.setCapability("deviceName", "device");
        des2.setCapability("noReset", true);
        //��ȡ��������һ��ҳ���activity
        des2.setCapability("appPackage", "com.growatt.shinetools");
        des2.setCapability("appActivity", "com.growatt.shinetools.module.account.LoginActivity");    //redmiNote11

        // ����appium����
        appiumDriver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), des2);
        appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeMethod(groups = "base")   // ÿ��methed����֮ǰ����һ��
    public void methodLevelSetup() {
        System.out.println("ִ��before method");
        loginPage = new LoginPage(appiumDriver);
        deviceHomePage = new DeviceHomePage(  appiumDriver);
        configsPage = new ConfigsPage(appiumDriver);
    }

    @BeforeGroups(groups = {"login","base"})
    public void init_login_group() throws MalformedURLException {
        System.out.println("init login group.");
        classLevelSetup();
    }

    @BeforeMethod(groups = {"base","login"})
    public void init_login_situation() throws InterruptedException {
        System.out.println("login�������before method��ʼִ��");
    }

    @AfterClass(groups = {"base"})
    public void teardown() {
        System.out.println("ִ��after class");
        // �رշ���
        appiumDriver.quit();
    }


}
