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

    static int maxRetryTime = 1;    // ʧ�����Դ���
    private  int retry = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retry < maxRetryTime){
            retry++;
            // ���Է���
            LoggerLoad.info("����ʧ�����Է��������ǵ� " + retry + " ������, ��׽�����쳣���£�\n" + iTestResult.getThrowable());
            handleUIAutoConfigTestFail();
            return true;
        }
        return false;
    }

    // ʧ�ܺ�Ͽ����ӣ��������ӻص�����ҳ��
    public void handleUIAutoConfigTestFail(){
        BaseCase.getDriver().quit();
        try {
            BaseCase.classLevelSetup();
            BaseCase.methodLevelSetup();
            LoggerLoad.debug("ʧ����������driver...success");
            SingleConfigCase.connectDeviceTest();
            LoggerLoad.debug("ʧ�����������豸...success");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
