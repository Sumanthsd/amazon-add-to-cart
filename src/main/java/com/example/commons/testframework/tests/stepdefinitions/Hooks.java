package com.example.commons.testframework.tests.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Hooks {
    public static WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
            // Set Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");

            // Initialize WebDriver
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

            // Clear cookies
            driver.manage().deleteAllCookies();

        // Set geolocation
        double latitude = 37.7749;
        double longitude = -122.4194;
        String setLocationScript = String.format(
                "navigator.geolocation.getCurrentPosition = function(success) { " +
                        "var position = { 'coords': { 'latitude': %s, 'longitude': %s }}; " +
                        "success(position); }", latitude, longitude);
        ((JavascriptExecutor) driver).executeScript(setLocationScript);

       LOGGER.info("Browser setup completed with geolocation and cookies cleared.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            LOGGER.info("Browser closed and WebDriver instance terminated.");
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}