package com.automationexcellence;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SampleTest {

    protected static Logger logger = LoggerFactory.getLogger(SampleTest.class);
    
    WebDriver driver;

    @BeforeMethod(alwaysRun=true)
    public void setUp() {
        // Headless arguments are critical for Jenkins execution
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        logger.info("About to launch the chrome driver");
        
        driver = new ChromeDriver(options);

        logger.info("chrome browser launched");
    }

    @Test(groups = {"Regression"})
    public void verifyGoogleTitle() {
        driver.get("https://google.com");
        String title = driver.getTitle();
        logger.info("Page title is: {}", title);
        Assert.assertTrue(title.contains("Google"), "Title does not match!");
        logger.info("test passed");
    }

    @Test(groups = {"Smoke"})
    public void playWithRelativeLocator()throws InterruptedException {
        driver.get("https://practicesoftwaretesting.com/");
        Thread.sleep(10000);
        WebElement homeLink = driver.findElement(By.xpath("//a[@data-test='nav-home']"));
        WebElement categories = driver.findElement(RelativeLocator.with(By.tagName("button")).toRightOf(homeLink));
        System.out.println(categories.getText());
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Quitting driver");
            driver.quit();
        }
    }
}
