package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By flashMessage = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open()
    {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void enterUserName(String username)
    {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password)
    {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin()
    {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password)
    {
        enterUserName(username);
        enterPassword(password);
        clickLogin();
    }

    public String getFlashMessage()
    {
        WebElement message= wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return message.getText();
    }

}
