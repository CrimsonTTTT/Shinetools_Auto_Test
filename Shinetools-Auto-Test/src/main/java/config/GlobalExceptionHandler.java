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
        // �ڲ��Է���ִ��ǰ���߼�

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // �ڲ��Է���ִ�к���߼�
        if (testResult.getThrowable() != null) {
            // ������Է����׳����쳣����������д���
            System.out.println("Test method " + method.getTestMethod().getMethodName() + " failed with exception: " + testResult.getThrowable().getMessage());
            // ������Լ�������쳣�����߼��������¼��־����ͼ��
            System.out.println("�ɹ���׽��һ���쳣����");
            System.out.println("׼���Ͽ������������ӣ���");

        }

    }
}
