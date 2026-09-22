package tests;

import base.BaseTest;
import base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ConfigReader;

public class LoginTest  extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void pageSetup(){
        loginPage = new LoginPage(driver);
    }

    @Test(priority=1 , description = "Verify user can login with valid credentials")
    public void verifySucessfullLogin(){

        String userName = ConfigReader.getProperty("username");
        String passWord = ConfigReader.getProperty("password");


        loginPage.login(userName, passWord);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory"), "Login  faild . Inventory page not displayed"
        );
    }


    @Test(priority = 2, description = "Verify login fails with invalid username")
    public void verifyLoginFailsWithInvalidUsername(){
        String invalidUserName = "invalid_user";
        String password = ConfigReader.getProperty("password");

        loginPage.login(invalidUserName, password);

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory"),
                "Invalid user name allowed the user to login"
        );

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Error message is  not displayed"
        );

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Incorrect error message displayed"
        );
    }

    @Test(priority = 3,  description = "Verify login fails with invalid password")
    public void VerifyLoginFailsWithInvalidPassword(){
        String userName = ConfigReader.getProperty("username");
        String password = "Invalid_Password";

        loginPage.login(userName, password);

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory"),
                        "Invalid password allowed the user to login"
        );

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Error message is  not displayed"

        );

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Incorrect error message displayed"
        );
    }



}
