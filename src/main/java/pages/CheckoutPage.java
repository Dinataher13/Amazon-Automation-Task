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

    By cashOnDeliveryCheck = By.xpath("//span[@class='a-color-base a-text-bold'][text()='الدفع عند الاستلام ']");
    By cashOnDeliveryConfirmButton = By.xpath("//input[@name='ppw-widgetEvent:SetPaymentPlanSelectContinueEvent'][@class='a-button-input a-button-text']");
    By deliveryFeeValue = By.xpath("//tr[contains(.,'رسوم الدفع عند الإستلام')]//td[2]");
    By totalOrder= By.xpath("//tr[contains(.,'إجمالي الطلب')]//td[2]");



    // Functions


    public CheckoutPage chooseCashOnDelivery (){
        wait.until(d -> {
        WebElement cashOnDeliveryElement = driver.findElement(cashOnDeliveryCheck);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", cashOnDeliveryElement);
        driver.findElement(cashOnDeliveryConfirmButton).click();
            return true;
        });
        return this;
    }

    public CheckoutPage getDeliveryFee (){
        wait.until(d -> {
       String deliveryFeeElement= driver.findElement(deliveryFeeValue).getText().replaceAll("[^0-9.]", "");
       String finalDeliveryFee = deliveryFeeElement.replace(".00","");
         orderDeliveryFee = Double.parseDouble(finalDeliveryFee);
            return true;
        });
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
    System.out.println("Total Order Price Assertion Is True ");
}

}
