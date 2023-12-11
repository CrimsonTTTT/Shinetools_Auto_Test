package config;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * TestNG Listener中的IReporter接口为我们提供了生成自定义报告的媒介（即自定义TestNG生成的报告）。
 * 该接口包含一个名为generateReport()的方法，当所有套件都运行时我们会调用该方法。该方法接受三个参数：
 xmlSuite：这是存在于 XML 文件中以供执行的套件列表。
 suites：这是一个对象，其中包含有关测试执行和套件相关信息的所有信息。此信息可能包括包名称、类名称、测试方法名称和测试执行结果。
 outputDirectory：它包含生成报告可用的输出文件夹的路径。
 */
public class GlobyReportConfig implements IReporter{


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        // 指定报告输出位置
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter report = new ExtentSparkReporter("Report/testReport.html");
        reports.attachReporter(report);

        // 创建报告
        //Iterate over every suite assigned for execution
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();
                ExtentTest menu_root_test = reports.createTest(tc.getName());

                buildTestNode(tc.getPassedTests(), Status.PASS, menu_root_test);
                buildTestNode(tc.getFailedTests(), Status.FAIL, menu_root_test);
                buildTestNode(tc.getSkippedTests(), Status.SKIP, menu_root_test);

                System.out.println("Passed tests for suite '" + suiteName +
                        "' is:" + tc.getPassedTests().getAllResults().size());
                System.out.println("Failed tests for suite '" + suiteName +
                        "' is:" + tc.getFailedTests().getAllResults().size());
                System.out.println("Skipped tests for suite '" + suiteName +
                        "' is:" + tc.getSkippedTests().getAllResults().size());
            }
        }
        reports.flush();
    }

    private void buildTestNode(IResultMap tests, Status status, ExtentTest father) {
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                System.out.println("itestResult：" + result);
                ExtentTest child = father.createNode(result.getMethod().getMethodName())
                        .log(status,result.getMethod().getDescription())
                        .log(status,"Method location path： "+result.getMethod().toString());
                for (String group : result.getMethod().getGroups())
                    child.assignCategory(group);

                if (result.getThrowable() != null) {
                    child.log(status, result.getThrowable());
                }
                else {
                    child.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }
                child.getModel().setStartTime(getTime(result.getStartMillis()));
                child.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}