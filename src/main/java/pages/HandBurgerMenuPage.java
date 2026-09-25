package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class HandBurgerMenuPage extends BasePage {

    private WebDriverWait wait;


    public HandBurgerMenuPage(WebDriver driver){


        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @FindBy(id= "react-burger-menu-btn")
    private WebElement menuButton;


    @FindBy(id= "react-burger-cross-btn")
    private WebElement closeButton;


    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;


    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;


    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;


    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;


    private By menuPanel = By.className("bm-menu-wrap");


    private By menuLinks =  By.cssSelector(".bm-item-list a");


    public void openMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(menuLinks));


    }
    // T31 - Menu opens
    public boolean isMenuOpen(){
        try{
            WebElement panal = driver.findElement((menuPanel));
            return panal.isDisplayed()
                    && "false".equals(
                    panal.getAttribute("aria-hidden")
            );


        }catch(Exception e){
            return false;
        }
    }


    // T32 - Menu closes using X


    public void closeMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();


        wait.until(ExpectedConditions.invisibilityOfElementLocated(menuPanel));
    }


    public boolean isMenuClosed(){
        try {
            WebElement panal = driver.findElement(menuLinks);
            return !panal.isDisplayed()
                    ||"true".equals(
                    panal.getAttribute("aria-hidden"));


        }catch (Exception e){
            return true;
        }
    }


    public boolean isCloseButtonDisplayed(){
        try{
            return closeButton.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }


    // T33 - Navigate to All Items


    public void clickAllItems(){
        wait.until(ExpectedConditions.elementToBeClickable(allItemsLink)).click();
    }


    public boolean isAllItemsLinkDisplayed(){
        return allItemsLink.isDisplayed();
    }
    // T34 - About redirects


    public void clickAbout(){
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
    }


    // T35 - Logout successfully


    public void clickLogOut(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }


    // T36 - Browser back after logout
    // Browser navigation belongs in the test.
    // This method allows the test to check whether
    // the menu is still available after navigation.


    public boolean isLogoutLinkDisplayed(){
        try{
            return logoutLink.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }


    // T37 / T38 - Reset app state


    public void clickResetAppState(){
        wait.until(ExpectedConditions.elementToBeClickable(resetAppStateLink)).click();
    }


    // T39 - Menu options in correct order
    // T40 / T43 - Menu content and text checks


    public List<String> getMenuOperations(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuPanel));


        List<WebElement> links = driver.findElements(menuLinks);


        return links.stream()
                .map((WebElement::getText))
                .toList();
    }


    public boolean areMenuOptionsInCorrectOrder(){
        List<String> expectedOptions = Arrays.asList(
                "All Items",
                "Dynamic Catalog",
                "About",
                "Logout",
                "Reset App State"
        );


        return getMenuOperations().equals(expectedOptions);
    }


    public boolean areMenuOptionsDisplayed(){
        List<WebElement> links = driver.findElements(menuLinks);


        return links.size() == 4
                && links.stream().allMatch(WebElement::isDisplayed);




    }


    // T41 - Close icon visible
    // Alignment can be checked separately using CSS.


    public String getCloseButtonCssValue(String property){
        return closeButton.getCssValue(property);
    }


    // T42 - Menu panel appears from the left
    // CSS-based check; exact values can vary by browser.




    public String getMenuPanelCssValue(String property) {
        return driver.findElement(menuPanel).getCssValue(property);
    }


    // T44 - Repeated clicking works


    public void clickMenuButton(){
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
    }


    public boolean isMenuButtonDisplayed(){
        try{
            return menuButton.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }
}



