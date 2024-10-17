package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class VideoGamesTest {
    WebDriver driver;


    @BeforeMethod
    public void setUp (){
        driver = new ChromeDriver();
    }

    @Test
    public void videoGamesScenario () throws InterruptedException {

        new HomePage(driver).navigation("https://www.amazon.eg/")
                .openLoginPage()
                .enterEmailOrUserName("").clickContinueButton()
                .enterPassword("").clickSignInButton()
                .openAllMenu()
                .openSeeAllDropDown().scrollToVideoGames().selectVideoGames()
                .checkFreeShipping().scrollToNew().selectNewFilter()
                .selectResultFromSortingList().addItemsToCart().goToCart()
                .getItemPrice().assertTotalPrice().goToCheckout()
                .openPaymentMethods().chooseCashOnDelivery()
                .getDeliveryFee().getTotalOrderPrice().assertTotalOrderPrice();
    }

    @AfterMethod
    public void tearDown (){
        driver.quit();
    }
}

