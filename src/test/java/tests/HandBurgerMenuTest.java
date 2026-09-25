
package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HandBurgerMenuPage;
import utilities.ConfigReader;

import java.util.Arrays;
import java.util.List;

import static base.DriverFactory.driver;

public class HandBurgerMenuTest extends BaseTest  {

    private LoginPage loginPage;
    private HandBurgerMenuPage handBurgerMenuPage;


    @BeforeMethod
    public void setUpMenuPage() {
        loginPage = new LoginPage(driver);
        // Step 2: Get credentials from config.properties
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");


        // Step 3: Login to SauceDemo
        loginPage.login(username, password);


        // Step 4: Verify successful login
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed. Inventory page not displayed"
        );


        // Step 5: Initialize MenuPage after login
        handBurgerMenuPage = new HandBurgerMenuPage(driver);
    }


    @Test(priority = 1, description = "Verify hamburger menu opens")
    public void verifyMenuOpens() {


        handBurgerMenuPage.openMenu();


        Assert.assertTrue(
                handBurgerMenuPage.isMenuOpen(),
                "Menu should open successfully"
        );
    }

    @Test(priority = 2, description = "Verify menu closes using X button")
    public void verifyMenuClosesUsingX(){
        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.closeMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuClosed(),
                "Menu should close after clicking X"
        );
    }
    @Test(priority = 3, description = "Verify All Items navigation")
    public void VerifyAllItemsNavigation(){
        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickAllItems();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Should navigate to the All Items page"
        );
    }


}