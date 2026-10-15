package tests;


import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HandBurgerMenuPage;
import pages.LoginPage;
import utilities.ConfigReader;

import java.time.Duration;


public class HandBurgerMenuTest extends BaseTest {
    private LoginPage loginPage;
    private HandBurgerMenuPage handBurgerMenuPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUpMenuPage(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);

        String userName = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        loginPage.login(userName, password);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed. Inventory page not displayed"
        );

        handBurgerMenuPage = new HandBurgerMenuPage(driver);

    }

    @Test(priority = 1, description = "T31 - Verify hamburger menu opens when menu icon is clicked")
    public void verifyMenuOpens(){
        handBurgerMenuPage.openMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuOpen(),
                "Menu should open successfully"
        );

    }


}