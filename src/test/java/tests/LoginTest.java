package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.CSVDataReader;
import utilities.ExcelReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

public class LoginTest extends BaseTest {
    private static final Logger logger =
            LogManager.getLogger(LoginTest.class);

    @Test(priority = 1)
    public void verifyValidLogin() {
        logger.info("Valid login test started");
        LoginPage loginpage = new LoginPage(driver);

        logger.info("Opening login page");
        loginpage.open();

        logger.info("Entering valid login credentials");
        loginpage.login("tomsmith", "SuperSecretPassword!");

        logger.info("Verifying successful login");

        String message = loginpage.getFlashMessage();

        Assert.assertTrue(message.contains("You logged into a secure area!"),
                "Success message was not displayed");

        Assert.assertTrue(driver.getCurrentUrl().contains("/secure"), "User was not redirected to secure area");

        logger.info("Valid login test completed");
    }

    @Test(priority = 2)
    public void verifyInvalidLogin() {

        logger.info("Invalid login test started");
        LoginPage Loginpage = new LoginPage(driver);

        logger.info("Opening login page");
        Loginpage.open();

        logger.info("Entering invalid credentials");
        Loginpage.login("tomsmith", "wrongPassword!");

        logger.info("Verifying error message");
        String message = Loginpage.getFlashMessage();
        System.out.println("Error Message:" + message);

        Assert.assertTrue(message.contains("Your password is invalid!"), "Expected error message was not displayed. Actual:" + message);
        logger.info("Invalid login test completed");

//  Adding this below assertion just to check failure test result screenshot is capturing into ExtentReport.
//        Assert.assertTrue(message.contains("THIS_MESSAGE_DOES_NOT_EXIST"),
//                "Intentional failure to test screenshot");

    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        //Below code we will use to take hardcoded input data.
//        return new Object[][]{
//                {"tomsmith", "wrongPassword123"},
//                {"wronguser", "SuperSecretPassword!"},
//                {"",""}

        //Below code we will use to take input data from csv file.
//        String filePath = Paths.get("src","test","resources","loginData.csv").toString();
//
//        return CSVDataReader.readCSV(filePath);
//        };

        //Below code we will use to take input data from .xlsx file.
//        String filePath = Paths.get(
//                "src", "test", "resources",
//                "loginData.xlsx"
//        ).toString();
//
//        return ExcelReader.readExcel(
//                filePath, "LoginData");

        //Below code we will use to take input data from .xlsx file and take blank value from Excel sheet.
        String filePath = Paths.get(
                "src", "test", "resources", "loginData.xlsx"
        ).toString();

        Object[][] data = ExcelReader.readExcel(filePath, "LoginData");

        for (Object[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if ("__BLANK__".equals(row[i])) {
                    row[i] = "";
                }
            }
        }

        return data;
    }

    @Test(priority = 3, dataProvider = "invalidLoginData")
    public void verifyInvalidLoginWithMultipleData(String username, String password) {
        logger.info("DataProvider login test started");
        LoginPage loginPage = new LoginPage(driver);

        logger.info("Opening login page");
        loginPage.open();

        logger.info("Attempting login with test data");
        loginPage.login(username, password);

        logger.info("Verifying login result");
        String message = loginPage.getFlashMessage();

        System.out.println(
                "Username:[" + username + "] | " +
                        "Password:[" + password + "] | " +
                        "Message:[" + message + "]"
        );

        if (username.isEmpty() || !username.equals("tomsmith")) {
            Assert.assertTrue(
                    message.contains("Your username is invalid!"),
                    "Expected username error was not displayed. Actual: " + message
            );
        } else {
            Assert.assertTrue(
                    message.contains("Your password is invalid!"),
                    "Expected password error was not displayed. Actual: " + message
            );
            logger.info("DataProvider login test completed");
        }
    }
}
