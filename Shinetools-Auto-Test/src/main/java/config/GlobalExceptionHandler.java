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
        // 在测试方法执行前的逻辑
        /*if (method.isTestMethod() && method.getTestMethod().getMethodName().equals("testWithData")) {
            // 在这里添加条件来决定是否执行测试用例
            boolean shouldRunTest = checkIfTestShouldRun(); // 你需要实现 checkIfTestShouldRun 方法
            if (!shouldRunTest) {
                testResult.setStatus(ITestResult.SKIP);
            }
        }*/
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // 获取当前日期和时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 格式化日期和时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        String fileName = "screenshots/bug" + formattedTime;

        // 在测试方法执行后的逻辑
        if (testResult.getThrowable() != null) {
            // 如果测试方法抛出了异常，在这里进行处理
            LoggerLoad.info("Test method " + method.getTestMethod().getMethodName() + " failed with exception: " + testResult.getThrowable().getMessage());
            // 这里可以加入你的异常处理逻辑，例如记录日志、截图等
            LoggerLoad.info("成功捕捉到一个异常！！截个图.");
            // 获取屏幕截图并保存为文件
            TakesScreenshot screenshotDriver = (TakesScreenshot)BaseCase.getDriver();
            File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
            // 移动文件到指定位置
            File destinationFile = new File(fileName);
            screenshotFile.renameTo(destinationFile);

            LoggerLoad.info("准备断开蓝牙重新连接！！");
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
