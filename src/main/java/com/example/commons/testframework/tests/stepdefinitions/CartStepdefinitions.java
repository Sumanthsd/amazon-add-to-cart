package com.example.commons.testframework.tests.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CartStepdefinitions {

    WebDriver driver;

    @Given("I open a Amazon website")
    public void openAmazonWebsite() {
        double latitude = 37.7749;
        double longitude = -122.4194;

        //Set Chrome Options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        //Instantiate WebDriver with ChromeOptions
        driver = new ChromeDriver(options);

        //Navigate to website
        driver.manage().deleteAllCookies();
        driver.get("https://www.amazon.in/");

        //Execute JavaScript to set the geolocation
        String setLocationScript = String.format("navigator.geolocation.getCurrentPosition = function(success) { var position = { 'coords': { 'latitude': %s, 'longitude': %s }}; success(position); }", latitude, longitude);
        ((JavascriptExecutor) driver).executeScript(setLocationScript);

        driver.manage().window().maximize();
        driver.navigate().refresh();
    }

    @When("I search for {string}")
    public void searchForProduct(String item) {
        WebElement searchBox = driver.findElement(By.xpath("//div[@id='nav-search']//input[@id='twotabsearchtextbox']"));
        searchBox.clear();
        searchBox.sendKeys(item);
        searchBox.submit();
    }

    @When("I select the first item from the results list")
    public void selectFirstItemFromTheList() {
        WebElement firstItem = driver.findElement(By.xpath("(//div[@data-component-type='s-search-result'])[1]//h2"));
        firstItem.click();
    }

    @When("I add first item to cart")
    public void addFirstItemToCart() {
        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement addToCartButton = driver.findElement(By.xpath("//span[@id='submit.add-to-cart']//input"));
        addToCartButton.click();
    }

    @Then("I should see the price identical to the product page")
    public void checkFirstPriceIdenticalToTheProductPage() {
        //Get product page price
        String productPagePrice = driver.findElement(By.xpath("(//div[@id='corePrice_feature_div']//span[@class='a-price-whole'])[1]")).getText();
        String[] productPriceBreakdown = productPagePrice.trim().split("\\s+");
        String productPrice = productPriceBreakdown[0] + ".00";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement closeButton = driver.findElement(By.xpath("(//a[contains(@id, 'attach-close_sideSheet-link')])[1]"));
        closeButton.click();

        WebElement myCart = driver.findElement(By.xpath("//div[@id='nav-tools']//a[@id='nav-cart']"));
        myCart.click();

        //Get cart page price
        String cartPagePrice = driver.findElement(By.xpath("(//div[@class='sc-item-price-block']//span)[1]")).getText();
        String finalCartPrice = cartPagePrice.replaceAll("[^0-9.,]", "").trim();
        String cartPrice = finalCartPrice;

        // Verify prices
        Assert.assertEquals(productPrice, cartPrice);
    }

    @Then("I should see the sub total identical to the product page")
    public void verifyCartSubTotalIdenticalToTheProductPage() {
        //Get product page subtotal
        String productPageSubTotal = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span")).getText();
        String[] productPriceBreakdown = productPageSubTotal.trim().split("\\s+");
        String productSubTotal = productPriceBreakdown[0];

        //Get cart page subtotal
        String cartPageSubTpotal = driver.findElement(By.xpath("//span[contains(text(),'Subtotal')]//parent::span/following-sibling::span")).getText();
        String[] cartPriceBreakdown = cartPageSubTpotal.trim().split("\\s+");
        String cartSubTotal = cartPriceBreakdown[0];

        //Verify subtotal
        Assert.assertEquals(productSubTotal, cartSubTotal);

        //Close the Browser
        driver.quit();
    }

    @Then("I should see the sub total of all items")
    public void verifyCartSubTotalofAllItems() {
        // Get Product page subtotal
        String productPageSubTotal = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span")).getText();
        String[] productPriceBreakdown = productPageSubTotal.trim().split("\\s+");
        String productSubTotal = productPriceBreakdown[0];

        //Get cart page subtotal
        String cartPageSubtotal = driver.findElement(By.xpath("//span[contains(text(),'Subtotal')]//parent::span/following-sibling::span")).getText();
        String[] cartPriceBreakdown = cartPageSubtotal.trim().split("\\s+");
        String cartSubtotal = cartPriceBreakdown[0];

        // Verify subtotal
        Assert.assertEquals(productSubTotal, cartSubtotal);

        // Close the browser
        driver.quit();
    }

    @When("I add second item to cart")
    public void addSecondItemToCart() {
        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement addToCartButton = driver.findElement(By.xpath("(//span[@id='submit.add-to-cart']//input)[2]"));
        addToCartButton.click();
    }

    @When("I select the second item from the results list")
    public void selectTheSecondItemFromTheList() {
        WebElement secondItem = driver.findElement(By.xpath("(//h2/a/span)[3]"));
        secondItem.click();
    }

    @Then("I should see the price identical to the second product page")
    public void checkSecondPriceIdenticalToTheProductPage() {
        //Get product page price
        String productPagePrice = driver.findElement(By.xpath("(//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole'])")).getText();
        String[] productPriceBreakdown = productPagePrice.trim().split("\\s+");
        String productPrice = productPriceBreakdown[0] + ".00";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement myCart = driver.findElement(By.xpath("//span[@id='attach-sidesheet-view-cart-button']"));
        myCart.click();

        //Get cart page price
        String cartPagePrice = driver.findElement(By.xpath("(//div[@class='sc-badge-price-to-pay']//span)[1]")).getText();
        String[] cartPriceBreakdown = cartPagePrice.trim().split("\\s+");
        String cartPrice = cartPriceBreakdown[0];

        // Verify prices
        Assert.assertEquals(productPrice, cartPrice);
    }

    @When("I add first searched item to cart")
    public void firstSearchedItemToCart() {
        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        //Get product page price
        String productPagePrice = driver.findElement(By.xpath("(//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole'])")).getText();
        String[] productPriceBreakdown = productPagePrice.trim().split("\\s+");
        String productPrice = productPriceBreakdown[0] + ".00";

        WebElement addToCartButton = driver.findElement(By.xpath("(//span[@id='submit.add-to-cart']//input)[1]"));
        addToCartButton.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


        WebElement myCart = driver.findElement(By.xpath("//a[@id='nav-cart']"));
        myCart.click();

        //Get cart page price
        String cartPagePrice = driver.findElement(By.xpath("//div[@class='sc-badge-price']//span")).getText();
        String[] cartPriceBreakdown = cartPagePrice.trim().split("\\s+");
        String cartPrice = cartPriceBreakdown[0];

        // Verify prices
        Assert.assertEquals(productPrice, cartPrice);
    }

    @When("I add second searched item to cart")
    public void secondSearchedItemToCart() {
        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));

        //Get product page price
        String productPagePrice = driver.findElement(By.xpath("(//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole'])")).getText();
        String[] productPriceBreakdown = productPagePrice.trim().split("\\s+");
        String productPrice = productPriceBreakdown[0] + ".00";

        WebElement addToCartButton = driver.findElement(By.xpath("(//span[@id='submit.add-to-cart']//input)[1]"));
        addToCartButton.click();

        WebElement myCart = driver.findElement(By.xpath("//span[@id='attach-sidesheet-view-cart-button']"));
        myCart.click();

        //Get cart page price
        String cartPagePrice = driver.findElement(By.xpath("(//div[@class='sc-badge-price']//span)[1]")).getText();
        String[] cartPriceBreakdown = cartPagePrice.trim().split("\\s+");
        String cartPrice = cartPriceBreakdown[0];

        // Verify prices
        Assert.assertEquals(productPrice, cartPrice);
    }
}