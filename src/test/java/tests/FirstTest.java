package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void verifyLoginTestResult(){
        String actualResult = "Success";
        String expectedResult = "Success";
        Assert.assertEquals(actualResult, expectedResult);

    }
}
