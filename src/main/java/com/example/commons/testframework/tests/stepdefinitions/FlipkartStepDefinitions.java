package com.example.commons.testframework.tests.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlipkartStepDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlipkartStepDefinitions.class);

    WebDriver driver;

    @Given("I open the Flipkart website")
    public void openFlipkartWebsite() {
        driver = Hooks.getDriver();
        driver.get("https://www.flipkart.com/");
        // Close login popup if it appears
        try {
            WebElement closeButton = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
            closeButton.click();
        } catch (Exception e) {
            LOGGER.info("Login popup not displayed.");
        }
    }

    @When("I search for {string} on Flipkart")
    public void searchForProductOnFlipkart(String item) {
        if (item == null || item.isEmpty()) {
            LOGGER.error("Search item is null or empty");
            throw new IllegalArgumentException("Search item cannot be null or empty");
        }
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(item);
        searchBox.submit();
    }

    @When("I select the first item from the Flipkart results list")
    public void selectFirstItemFromFlipkartList() {
        WebElement firstItem = driver.findElement(By.xpath("(//div[@class='_1AtVbE'])[2]//a"));
        firstItem.click();
    }

    @When("I add the first item to the Flipkart cart")
    public void addFirstItemToFlipkartCart() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]"));
        addToCartButton.click();
    }

    @Then("I should see the Flipkart price identical to the product page")
    public void checkFlipkartPriceIdenticalToProductPage() {
        String productPagePrice = driver.findElement(By.xpath("//div[@class='_30jeq3 _16Jk6d']")).getText().replace("₹", "").replace(",", "").trim();

        WebElement cartButton = driver.findElement(By.xpath("//span[text()='Cart']"));
        cartButton.click();

        String cartPagePrice = driver.findElement(By.xpath("//span[@class='_2-ut7f _1WpvJ7']")).getText().replace("₹", "").replace(",", "").trim();

        Assert.assertEquals(productPagePrice, cartPagePrice);
    }
}