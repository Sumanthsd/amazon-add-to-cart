package com.example.commons.testframework.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/main/resources",
        glue = {"com.example.commons.testframework.tests.stepdefinitions"},
        plugin = {"pretty",
                  "html:test-output/reports/cucumber/multiple.html",
                  "json:target/report,json,",
                  "junit:target/junit.xml"}
)

public class TestNGSuiteRunner extends AbstractTestNGCucumberTests{

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() { return super.scenarios(); }
}