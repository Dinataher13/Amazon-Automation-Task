package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    Wait<WebDriver> wait;

    // constructor

    public LoginPage (WebDriver driver){
        this.driver= driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    // Locators
    By emailTextField = By.id("ap_email");
    By continueButton = By.id("continue");
    By passwordTextField = By.id("ap_password");
    By signInButton = By.id("signInSubmit");

    // Functions

    public LoginPage enterEmailOrUserName (String email) {

        wait.until(d -> {
           driver.findElement(emailTextField).sendKeys(email);
            return true;
        });
        return this;
    }


    public LoginPage clickContinueButton (){
        driver.findElement(continueButton).click();
        return this;
    }

    public LoginPage enterPassword (String password) {
        driver.findElement(passwordTextField).sendKeys(password);
        return this;
    }

    public HomePage clickSignInButton (){
        wait.until(d -> {
        driver.findElement(signInButton).click();
            return true;
        });
        return new HomePage(driver);
    }
}
