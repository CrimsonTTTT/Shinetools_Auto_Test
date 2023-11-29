package config;

import org.openqa.selenium.TimeoutException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/6 20:13
 * @Descripe
 */
public class GlobalExceptionHandler implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // 在测试方法执行前的逻辑

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // 在测试方法执行后的逻辑
        if (testResult.getThrowable() != null) {
            // 如果测试方法抛出了异常，在这里进行处理
            System.out.println("Test method " + method.getTestMethod().getMethodName() + " failed with exception: " + testResult.getThrowable().getMessage());
            // 这里可以加入你的异常处理逻辑，例如记录日志、截图等
            System.out.println("成功捕捉到一个异常！！");
            System.out.println("准备断开蓝牙重新连接！！");

        }

    }
}
