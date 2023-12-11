package config;

import io.appium.java_client.AppiumDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import pages.LoginPage;
import testcase.BaseCase;
import testcase.SingleConfigCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/24 14:13
 * @Descripe
 */
public class RetryHandler implements IRetryAnalyzer {

    static int maxRetryTime = 1;    // 失败重试次数
    private  int retry = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retry < maxRetryTime){
            retry++;
            // 重试方法
            LoggerLoad.info("进入失败重试方法，这是第 " + retry + " 次重试, 捕捉到的异常如下：\n" + iTestResult.getThrowable());
            handleUIAutoConfigTestFail();
            return true;
        }
        return false;
    }

    // 失败后断开连接，重新连接回到设置页面
    public void handleUIAutoConfigTestFail(){
        BaseCase.getDriver().quit();
        try {
            BaseCase.classLevelSetup();
            BaseCase.methodLevelSetup();
            LoggerLoad.debug("失败重新连接driver...success");
            SingleConfigCase.connectDeviceTest();
            LoggerLoad.debug("失败重新连接设备...success");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
