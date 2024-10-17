package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HomePage {
   WebDriver driver;
    Wait<WebDriver> wait;

    // constructor
    public HomePage (WebDriver driver){
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
    }


    // Locators
    By signIn = By.id("nav-link-accountList-nav-line-1");
    By allButton = By.id("nav-hamburger-menu");
    By seeAllDropDown = By.xpath("//*[@id='hmenu-content']//a[@class='hmenu-item hmenu-compressed-btn']");
    By homeTools = By.xpath("//a[@class='hmenu-item'][@data-menu-id='12']");
    By videoGames = By.xpath("//a[@class='hmenu-item'][text()='ألعاب الفيديو']");
    By allVideoGames = By.xpath("//*[@id='hmenu-container']//a[@class='hmenu-item'][text()='جميع ألعاب الفيديو']");





// Functions
    public HomePage navigation (String URL){
        driver.navigate().to(URL);
        return this;
    }

    public LoginPage openLoginPage (){
        wait.until(d -> {
            driver.findElement(signIn).click();
        return true;
        });
         return new LoginPage(driver);
    }

    public HomePage openAllMenu(){
        wait.until(d -> {
        driver.findElement(allButton).click();
            return true;
        });
        return this;
    }

    public HomePage openSeeAllDropDown (){
        wait.until(d -> {
        driver.findElement(seeAllDropDown).click();
            return true;
        });
        return this;
    }

    public HomePage scrollToVideoGames () throws InterruptedException {
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement VideoGames = driver.findElement(videoGames);
           new Actions(driver).
                   moveToElement(VideoGames).perform();

        return this;

    }

    public VideoGamesPage selectVideoGames (){
        wait.until(d -> {
            WebElement VideoGames = driver.findElement(videoGames);
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", VideoGames);
//            driver.findElement(videoGames).click();
            return true;
        });
        return new VideoGamesPage(driver);
    }


    }



