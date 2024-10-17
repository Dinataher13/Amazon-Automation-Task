package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    Wait<WebDriver> wait;
    double orderDeliveryFee;
    double  totalOrderPrice;

    // constructor
    public CheckoutPage (WebDriver driver){
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NotFoundException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    // Locators
    By paymentMethod = By.xpath("(//div[@class='a-column a-span10'])[1]");
    By cashOnDeliveryCheck = By.id("//input[@id='pp-rlt6nn-80");
    By cashOnDeliveryConfirmButton = By.id("pp-84nQwi-81-announce");
    By deliveryFeeValue = By.xpath("//tr[contains(.,'الشحن والتسليم')]//td[2]");
    By totalOrder= By.xpath("//tr[contains(.,'إجمالي الطلب')]//td[2]");



    // Functions

    public CheckoutPage openPaymentMethods (){
        driver.findElement(paymentMethod).click();
        return this;
    }

    public CheckoutPage chooseCashOnDelivery (){
        driver.findElement(cashOnDeliveryCheck).click();
        driver.findElement(cashOnDeliveryConfirmButton).click();
        return this;
    }

    public CheckoutPage getDeliveryFee (){
       String deliveryFeeElement= driver.findElement(deliveryFeeValue).getText().replaceAll("[^0-9.]", "");
       String finalDeliveryFee = deliveryFeeElement.replace(".00","");
         orderDeliveryFee = Double.parseDouble(finalDeliveryFee);
        return this;
    }

    public CheckoutPage getTotalOrderPrice (){
       String totalOrderNumberOnly = driver.findElement(totalOrder).getText().replaceAll("[^0-9.]", "");
       String totalOrderWithoutex = totalOrderNumberOnly.replace(".00","");
       totalOrderPrice = Double.parseDouble(totalOrderWithoutex);
       return this;

    }


public void assertTotalOrderPrice (){
        double delFeeAndcartTotalPriceSum = orderDeliveryFee + CartPage.cartTotalPrice;
        Assert.assertEquals(totalOrderPrice,delFeeAndcartTotalPriceSum);
}

}
