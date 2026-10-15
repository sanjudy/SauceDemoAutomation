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

   public static final List<String> EXPECTED_MENU_OPTIONS = Arrays.asList(
           "All Items",
           "Dynamic Catalog",
           "About",
           "Logout",
           "Reset App State"

   );

   private final WebDriverWait wait;

   public HandBurgerMenuPage(WebDriver driver){
       super(driver);
       PageFactory.initElements(driver, this);
       wait = new WebDriverWait(driver, Duration.ofSeconds(10));

   }

   @FindBy(id = "react-burger-menu-btn")
   private  WebElement reactBurgerMenuBtn;

   @FindBy(id = "react-burger-cross-btn")
   private WebElement reactBurgerCrossBtn;

   @FindBy(id = "inventory_sidebar_link")
   private WebElement inventorySidebarLink;

   @FindBy(id = "dynamic_catalog_sidebar_link")
   private WebElement dynamicCatalogSidebarLink;

   @FindBy(id = "about_sidebar_link")
   private WebElement aboutSidebarLink;

   @FindBy(id = "reset_sidebar_link")
   private WebElement resetSidebarLink;


   private  final By menuPanal = By.className("bm-menu-wrap");
   private final By  menuLinks = By.className("bm-item-list");


   public void openMenu(){
       wait.until(ExpectedConditions.elementToBeClickable(reactBurgerMenuBtn)).click();
       wait.until(ExpectedConditions.attributeToBe(menuPanal, "aria-hidden","false"));
       wait.until(ExpectedConditions.visibilityOfElementLocated(menuLinks));
   }

   public boolean isMenuOpen(){
       try{
           WebElement panal  = driver.findElement(menuPanal);
           return panal.isDisplayed()
                   && "false".equals(panal.getAttribute("aria-hidden"));

       } catch (Exception e) {
           return false;
       }
   }

}