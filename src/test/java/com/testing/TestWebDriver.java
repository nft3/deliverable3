package com.testing;

import junit.framework.Assert;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Nicholas Treu
 */
public class TestWebDriver {

    WebDriver driver;

    @Before
    // Instantiate a new FirefoxDriver object
    public void setup(){
        driver = new FirefoxDriver();
    }

    @After
    // Close any connections that we have made testing.
    public void tearDown(){
        driver.close();
    }

    @Test
    public void checkGithubTitle(){

        // Navigate to Google just to test
        driver.navigate().to("https://www.github.com");

        int example = 5;

        // Test if the title of the google.com is, in fact, "Google"
        Assert.assertEquals("GitHub · Where software is built", driver.getTitle());
    }
}