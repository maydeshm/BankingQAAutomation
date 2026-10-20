package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    public WebDriver driver;
    private static final Logger logger =
            LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
//        System.out.println("Before Method: Browser setup started");
//        We are replacing syetem.out.println with logger.info to fetch logs.
        logger.info("Browser setup started");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Chrome browser launched and maximized");
    }
    public String captureScreenshot(String screenshotName) {

        try {
    File source = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

    Path screenshotDir = Paths.get(
            "target", "screenshots");

        Files.createDirectories(screenshotDir);

    Path destination = screenshotDir.resolve(
            screenshotName + "_" +
                    System.currentTimeMillis() + ".png");

        Files.copy(source.toPath(), destination);

        return destination.toAbsolutePath().toString();

} catch (IOException e) {
        throw new RuntimeException(
                "Unable to capture screenshot", e);
    }
            }

    @AfterMethod
    public void tearDown()
    {
//        System.out.println("After Method: Browser cleanup started");
        logger.info("Browser cleanup started");
        if (driver!=null)
        {
            driver.quit();
            logger.info("Browser closed successfully");
        }

    }

}
