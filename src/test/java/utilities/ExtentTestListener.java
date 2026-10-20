
package utilities;

import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;
import org.openqa.selenium.WebDriver;
import java.io.File;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        ExtentReportManager.createTest(
                result.getMethod().getMethodName());

        ExtentReportManager.getTest().info(
                "Test execution started");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentReportManager.getTest().fail(
                result.getThrowable());

        Object instance = result.getInstance();

        if (instance instanceof BaseTest) {

            BaseTest baseTest = (BaseTest) instance;

            try {
                String screenshotPath =
                        baseTest.captureScreenshot(
                                result.getMethod().getMethodName());

                ExtentReportManager.getTest()
                        .addScreenCaptureFromPath(screenshotPath);

            } catch (Exception e) {
                ExtentReportManager.getTest().warning(
                        "Screenshot could not be attached: "
                                + e.getMessage());
            }
        }

        ExtentReportManager.unload();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass(
                "Test passed successfully");
        ExtentReportManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentReportManager.getTest().skip(
                "Test was skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentReportManager.getReportInstance().flush();
    }
}
