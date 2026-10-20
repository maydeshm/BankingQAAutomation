
package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    public static synchronized ExtentReports getReportInstance() {

        if (extent == null) {

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            "target/ExtentReport.html");

            sparkReporter.config().setDocumentTitle(
                    "Banking QA Automation Report");

            sparkReporter.config().setReportName(
                    "Login Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Project", "BankingQAAutomation");

            extent.setSystemInfo(
                    "Testing Type", "Web UI Automation");
        }

        return extent;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest =
                getReportInstance().createTest(testName);

        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void unload() {
        test.remove();
    }
}
