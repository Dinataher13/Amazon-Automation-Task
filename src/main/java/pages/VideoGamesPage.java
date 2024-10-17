package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoGamesPage {
    WebDriver driver;
    Wait<WebDriver> wait;
    double actualPrice;
    ArrayList<String> addedCartItems = new ArrayList<>();



    // constructor
    public VideoGamesPage (WebDriver driver){
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    // Locators
    By freeShippingCheckBox = By.xpath("//div//span[@class='a-size-base a-color-base'][text()='الشحن المجاني']");
    By newButton = By.xpath("//*[@class='a-size-base a-color-base'][text()='جديد']");
    By sortingDropDown = By.id("s-result-sort-select");
    By addToCartButton;
    By nextButton = By.xpath("//a[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-separator']");
    By cartButton = By.xpath("//div[@id='ewc-compact-actions-container']//a[@class='a-button-text']");


    // Functions

    public VideoGamesPage checkFreeShipping (){
        driver.findElement(freeShippingCheckBox).click();
        return this;
    }

    public VideoGamesPage scrollToNew () throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new Actions(driver).moveToElement(driver.findElement(newButton)).perform();
        return this;

    }

    public VideoGamesPage selectNewFilter (){
        driver.findElement(newButton).click();
        return this;
    }



    public VideoGamesPage selectResultFromSortingList (){
        wait.until(d -> {
        Select dropDown = new Select(driver.findElement(sortingDropDown));
        dropDown.selectByVisibleText("السعر: من الأعلى إلى الأدنى");
            return true;
        });
        return this;
    }


    public VideoGamesPage addItemsToCart (){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int buttonIndex = 1;
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='sg-col-20-of-24 s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16']"));
         for(int i = 0; i < items.size(); i++){
             WebElement itemElement = items.get(i);
             var index = i + 1;
             try {
                 WebElement itemPriceElement = itemElement.findElement(By.xpath("(//div//span[@class='a-price-whole'])["+index+"]"));
                 if (itemPriceElement!=null){
                     String itemPrice = itemPriceElement.getText();
                     String numberWithoutComma = itemPrice.replace(",", "");
                     actualPrice = Double.parseDouble(numberWithoutComma);
                 }
                 //Add To Cart

                 WebElement addToCart = itemElement.findElement(By.xpath("(//div[@class='a-section puis-atcb-add-container aok-inline-block']//button[@class='a-button-text'])["+buttonIndex+"]"));

                 if (addToCart != null &&  actualPrice < 13000){
                     buttonIndex += 1;
                     JavascriptExecutor executor = (JavascriptExecutor)driver;
                     executor.executeScript("arguments[0].click();", addToCart);
                     WebElement addedItemElement = itemElement.findElement(By.xpath("(//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-2'])["+index+"]"));
                     addedCartItems.add(addedItemElement.getText());

                 }
             } catch (NoSuchElementException exception) {
                 System.out.println(exception.toString());
             }

        }
        System.out.println("======== Added cart Items =====================");
        System.out.println(addedCartItems);
        System.out.println("======== Added cart Items =====================");
return this;
    }

     public CartPage goToCart (){
        WebElement addToCartElement = driver.findElement(cartButton);
         JavascriptExecutor executor = (JavascriptExecutor)driver;
         executor.executeScript("arguments[0].click();", addToCartElement);
         return new CartPage(driver);

     }




}
