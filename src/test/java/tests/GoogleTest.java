package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {
    @Test
    public void VerifyGoogleTitle()
    {
        String actualTitle = driver.getTitle();
        System.out.println("Page Title: " + actualTitle);

        Assert.assertTrue(actualTitle.contains("Google"),"Google page title validation failed");
    }
}
