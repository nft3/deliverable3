package com.testing;

import static org.junit.Assert.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Nicholas Treu
 * As a logged in user of reddit.com
 * I want to have changed attributes of my proile
 * So that I can personalize it for my needs.
 */
public class RedditLoggedInTester {

    WebDriver driver;

    @Before
    // Instantiate a WebDriver object and log into Reddit.com
    public void setup(){
        // Open a Firefox browser and got to reddit.com
        driver = new FirefoxDriver();
        driver.get("https://www.reddit.com");

        // Log into reddit.com with a dumby class made for this assignment
        WebElement username = driver.findElement(By.name("user"));
        WebElement password = driver.findElement(By.name("passwd"));

        // Send keys of a test dumby account created for this assignment
        username.sendKeys("cs1632testdumby");
        password.sendKeys("password");

        // Push the login button
        WebElement login = driver.findElement(By.id("login_login-main"));
        login.findElement(By.className("btn")).click();
    }

    @After
    // Close the driver
    public void tearDown(){
        driver.close();
    }

    @Test
    /*
        Given that I am logged into Reddit
        Then I want to change my preferences so that
        When I click a link, it will open in a new window
    */
    public void testOpenLinksInNewWindow(){
        By css = By.cssSelector("a[href='https://www.reddit.com/prefs/']");
        WebElement preferences = driver.findElement(css);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();" , preferences);
        System.out.println(driver.getTitle());
    }


}
