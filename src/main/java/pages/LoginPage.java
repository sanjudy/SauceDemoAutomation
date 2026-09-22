package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver){
      super(driver);
      PageFactory.initElements(driver, this);

    }
    @FindBy(id = "user-name")
    private WebElement usernameTextBox;

    @FindBy(id = "password")
    private WebElement passwordTextBox;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;



    public void enterUsername(String userName){
        type(usernameTextBox, userName);
    }

    public void enterPassword(String password){
        type(passwordTextBox, password);
    }

    public void clickLogin(){
        click(loginButton);
    }

    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();

    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLoginPageDisplayed() {
        return idDisplayed(loginLogo);
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

}

