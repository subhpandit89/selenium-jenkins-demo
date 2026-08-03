package com.automationexcellence;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.automationexcellence.testManager.ExtentReportManager;
import com.automationexcellence.testManager.ExtentTestManager;

public class SampleTest {

    protected static Logger logger = LoggerFactory.getLogger(SampleTest.class);
    
    WebDriver driver;

    @BeforeSuite
    public void suiteLevelSetup(){
        ExtentReportManager.createExtentReport();
    }

    @BeforeMethod(alwaysRun=true)
    public void setUp(Method method) {
        // Headless arguments are critical for Jenkins execution
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        logger.info("About to launch the chrome driver");
        
        driver = new ChromeDriver(options);

        logger.info("chrome browser launched");
        ExtentTestManager.initializeTest(method.getName(), method.getDeclaredAnnotation(Test.class).description());
    }

    @Test(groups = {"Regression"}, description = "Verify google page title")
    public void verifyGoogleTitle() {
        driver.get("https://google.com");
        String title = driver.getTitle();
        logger.info("Page title is: {}", title);
        ExtentTestManager.getTest().info("Page title is: " + title);
        Assert.assertTrue(title.contains("Google"), "Title does not match!");
        logger.info("test passed");
        ExtentTestManager.getTest().info("Test Passed");
    }

    @Test(groups = {"Smoke"}, description = "Test Selenium 4 features")
    public void playWithRelativeLocator()throws InterruptedException {
        driver.get("https://practicesoftwaretesting.com/");
        ExtentTestManager.getTest().info("Navigated to practicesoftwaretesting.com");
        Thread.sleep(10000);
        WebElement homeLink = driver.findElement(By.xpath("//a[@data-test='nav-home']"));
        WebElement categories = driver.findElement(RelativeLocator.with(By.tagName("button")).toRightOf(homeLink));
        System.out.println(categories.getText());

        ExtentTestManager.getTest().info("RelativeLocator is working");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Quitting driver");
            driver.quit();
        }

        ExtentTestManager.removeTest();
    }

    @AfterSuite(alwaysRun=true)
    public void finishSuite(){
        ExtentReportManager.flushReport();
    }
}
