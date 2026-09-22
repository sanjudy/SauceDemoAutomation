
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HandBurgerMenuPage;
import utilities.ConfigReader;

import java.util.Arrays;
import java.util.List;

public class HandBurgerMenuTest extends BaseTest {
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

    // T31 - Menu opens
    // T32 - Menu closes using X
    @Test(priority = 2, description = "Verify menu closes using X button")
    public void verifyMenuClosesUsingX() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.closeMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuClosed(),
                "Menu should close after clicking X"
        );
    }

    // T33 - Navigate to All Items
    @Test(priority = 3, description = "Verify All Items navigation")
    public void verifyAllItemsNavigation() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickAllItems();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Should navigate to the All Items page"
        );
    }

    // T34 - About redirects
    @Test(priority = 4, description = "Verify About redirects to Sauce Labs")
    public void verifyAboutNavigation() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickAbout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucelabs.com"),
                "Should navigate to the Sauce Labs website"
        );
    }

    // T35 - Logout successfully
    @Test(priority = 5, description = "Verify user can logout successfully")
    public void verifyLogoutSuccessfully() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickLogOut();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "Should remain on the SauceDemo domain"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("index.html")
                        || driver.getCurrentUrl().endsWith("/"),
                "Should navigate to the login page"
        );
    }

    // T36 - Browser back after logout
    @Test(priority = 6, description = "Verify browser back after logout")
    public void verifyBrowserBackAfterLogout() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickLogOut();

        driver.navigate().back();

        // Verify protected inventory page is not accessible.
        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory.html"),
                "Logged-out user should not access inventory page"
        );
    }

    // T37 / T38 - Reset app state
    @Test(priority = 7, description = "Verify Reset App State")
    public void verifyResetAppState() {

        handBurgerMenuPage.openMenu();
        handBurgerMenuPage.clickResetAppState();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "User should remain on the inventory page"
        );
    }

    // T39 - Menu options in correct order
    @Test(priority = 8, description = "Verify menu options order")
    public void verifyMenuOptionsOrder() {

        handBurgerMenuPage.openMenu();

        List<String> expectedOptions = Arrays.asList(
                "All Items",
                "Dynamic Catalog",
                "About",
                "Logout",
                "Reset App State"
        );

        Assert.assertEquals(
                handBurgerMenuPage.getMenuOperations(),
                expectedOptions,
                "Menu options should appear in the expected order"
        );
    }

    // T40 / T43 - Menu content and text checks
    @Test(priority = 9, description = "Verify all menu options are displayed")
    public void verifyMenuOptionsDisplayed() {

        handBurgerMenuPage.openMenu();

        Assert.assertTrue(
                handBurgerMenuPage.areMenuOptionsDisplayed(),
                "All four menu options should be displayed"
        );
    }

    // T41 - Close icon visible
    @Test(priority = 10, description = "Verify close button is displayed")
    public void verifyCloseButtonDisplayed() {

        handBurgerMenuPage.openMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isCloseButtonDisplayed(),
                "Close button should be displayed"
        );
    }

    // T42 - Menu panel appears from the left
    @Test(priority = 11, description = "Verify menu panel positioning")
    public void verifyMenuPanelPosition() {

        handBurgerMenuPage.openMenu();

        String position =
                handBurgerMenuPage.getMenuPanelCssValue("position");

        Assert.assertEquals(
                position,
                "fixed",
                "Menu panel should use fixed positioning"
        );
    }

    // T44 - Repeated clicking works
    @Test(priority = 12, description = "Verify repeated menu interactions")
    public void verifyRepeatedMenuClicking() {

        handBurgerMenuPage.openMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuOpen(),
                "Menu should open on first click"
        );

        handBurgerMenuPage.closeMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuClosed(),
                "Menu should close after clicking X"
        );

        handBurgerMenuPage.openMenu();

        Assert.assertTrue(
                handBurgerMenuPage.isMenuOpen(),
                "Menu should open again"
        );
    }
}