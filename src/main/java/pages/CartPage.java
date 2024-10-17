package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CartPage {
    WebDriver driver;
    Wait<WebDriver> wait;
    ArrayList<Double> cartItemPrices = new ArrayList<>();
   public static double cartTotalPrice;



    // constructor
    public CartPage (WebDriver driver){
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    //Locators
    By totalPrice = By.xpath("//*[@id='sc-subtotal-amount-activecart'][@class='a-color-price sc-price-container a-text-bold']");
    By checkoutButton = By.xpath("//span[@id='sc-buy-box-ptc-button']");



    public CartPage getItemPrice () {

        List<WebElement> itemPrices = driver.findElements(By.xpath("//div[@class='a-row sc-list-item sc-java-remote-feature']//div[@class='a-checkbox a-checkbox-fancy sc-item-check-checkbox-selector sc-list-item-checkbox']"));
        for (int i = 0; i < itemPrices.size(); i++) {
            WebElement itemElement = itemPrices.get(i);
            var index = i + 1;
            WebElement itemPriceElement = itemElement.findElement(By.xpath("(//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold'])[" + index + "]"));
            String addedItemPrice = itemPriceElement.getText().replaceAll("[^0-9.]", "");
            String itemNetPrice = addedItemPrice.replace(".00","");
            double itemPriceInList =  Double.parseDouble(itemNetPrice);
            cartItemPrices.add(itemPriceInList);
        }
        System.out.println(cartItemPrices);
         cartTotalPrice = cartItemPrices.stream().mapToDouble(p -> p).sum();
        System.out.println(cartTotalPrice);
        return this;

        }

        public CartPage assertTotalPrice (){
        String totalPriceElement = driver.findElement(totalPrice).getText().replaceAll("[^0-9.]", "");
        String finalPrice = totalPriceElement.replace(".00","");
        double cartFinalPrice = Double.parseDouble(finalPrice);
            Assert.assertEquals(cartFinalPrice,cartTotalPrice);

return this;
        }

        public CheckoutPage goToCheckout (){
        driver.findElement(checkoutButton).click();
return new CheckoutPage(driver);
        }


    }
