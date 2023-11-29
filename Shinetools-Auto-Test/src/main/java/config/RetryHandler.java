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
            // ���Է���
            System.out.println("����ʧ�����Է��������ǵ� " + retry + " ��");

            handleUIAutoConfigTestFail();

            return true;
        }
        return false;
    }

    public void handleUIAutoConfigTestFail(){

    }
}
