package config;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import testcase.BaseCase;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/6 20:13
 * @Descripe
 */
public class GlobalExceptionHandler implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // �ڲ��Է���ִ��ǰ���߼�
        /*if (method.isTestMethod() && method.getTestMethod().getMethodName().equals("testWithData")) {
            // ��������������������Ƿ�ִ�в�������
            boolean shouldRunTest = checkIfTestShouldRun(); // ����Ҫʵ�� checkIfTestShouldRun ����
            if (!shouldRunTest) {
                testResult.setStatus(ITestResult.SKIP);
            }
        }*/
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // ��ȡ��ǰ���ں�ʱ��
        LocalDateTime currentTime = LocalDateTime.now();
        // ��ʽ�����ں�ʱ��
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        String fileName = "screenshots/bug" + formattedTime;

        // �ڲ��Է���ִ�к���߼�
        if (testResult.getThrowable() != null) {
            // ������Է����׳����쳣����������д���
            LoggerLoad.info("Test method " + method.getTestMethod().getMethodName() + " failed with exception: " + testResult.getThrowable().getMessage());
            // ������Լ�������쳣�����߼��������¼��־����ͼ��
            LoggerLoad.info("�ɹ���׽��һ���쳣�����ظ�ͼ.");
            // ��ȡ��Ļ��ͼ������Ϊ�ļ�
            TakesScreenshot screenshotDriver = (TakesScreenshot)BaseCase.getDriver();
            File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
            // �ƶ��ļ���ָ��λ��
            File destinationFile = new File(fileName);
            screenshotFile.renameTo(destinationFile);

            LoggerLoad.info("׼���Ͽ������������ӣ���");
            /*try {
                reconnectTry(BaseCase.getDriver());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }*/
        }
    }
/*
    public void reconnectTry(AppiumDriver driver) throws MalformedURLException {
        driver.quit();
        BaseCase.classLevelSetup();
    }*/
}
