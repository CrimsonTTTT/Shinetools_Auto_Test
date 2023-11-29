package config;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/24 14:13
 * @Descripe
 */
public class RetryHandler implements IRetryAnalyzer {

    static int maxRetryTime = 3;
    private  int retry = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retry < maxRetryTime){
            retry++;
            // 重试方法
            System.out.println("进入失败重试方法，这是第 " + retry + " 次");

            handleUIAutoConfigTestFail();

            return true;
        }
        return false;
    }

    public void handleUIAutoConfigTestFail(){

    }
}
