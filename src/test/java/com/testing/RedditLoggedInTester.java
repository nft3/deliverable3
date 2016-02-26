package com.testing;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.List;

/**
 * @author Nicholas Treu
 * As a logged in user of reddit.com
 * I want to have changed attributes of my proile
 * So that I can personalize it for my needs.
 */
public class RedditLoggedInTester {

    WebDriver driver;

    @Before
    // Instantiate a WebDriver object and log into Reddit.com using a test dumby account.
    public void setup() throws NoSuchElementException {
        // Open a Firefox browser and go to reddit.com
        driver = new FirefoxDriver();
        driver.get("https://www.reddit.com");

        // Just delete all the cookies for a clean log in attempt

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
        driver.quit();
    }

    @Test
    /*
        Given that I am logged into Reddit
        Then I want to change my preferences so that
        When I click a link, it will open in a new window
    */
    public void testOpenLinksInNewWindow() {
        try {
            // Click on the preference button
            WebElement preference = driver.findElement(By.xpath("/html/body/div[1]/div[3]/ul/li/a"));
            preference.click();
            System.out.println("Click the preference button");

            // Check the box that says open page in new window
            WebElement checkbox = driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[2]/td/label"));
            checkbox.click();
            System.out.println("Click the checkbox button");

            // Now click the save button to save these preferences
            WebElement submitButton = driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.loggedin " +
                    "div.content form.pretty-form.short-text table.content.preftable tbody tr td input.btn"));
            submitButton.click();
            System.out.println("Click the submit button");

            // Navigate back to the first page
            driver.navigate().back();
            System.out.println("Click the back button");

            // Get the first item on the Reddit front page
            WebElement table = driver.findElement(By.id("siteTable"));
            List<WebElement> rows = table.findElements(By.className("title may-blank "));
            for(WebElement w : rows)
                System.out.println(w);

            // Click the first link on the page
        } catch(ElementNotFoundException e){
            fail();
        }
    }

    @Test
    /*
        Given that I am logged into Reddit.com
        Then I want to access all the preference tabs
        When I want to make changes to my profile.
     */
    public void testLoggedInTabs() {
        String[] urls = {"https://www.reddit.com/prefs/", "https://www.reddit.com/prefs/apps/",
                "https://www.reddit.com/prefs/feeds/", "https://www.reddit.com/prefs/friends/",
                "https://www.reddit.com/prefs/blocked/", "https://www.reddit.com/prefs/update/",
                "https://www.reddit.com/prefs/delete/"};


        // Click on the preference button. Have to access some nested DOM elements
        WebElement header = driver.findElement(By.id("header"));
        System.out.println("founder header");

        WebElement headerRight = header.findElement(By.id("header-bottom-right"));
        WebElement flatlisthover = headerRight.findElement(By.className("flat-list hover"));
        WebElement preference = flatlisthover.findElement(By.className("pref-lang choice"));
        preference.click();
        System.out.println("Click the preference button");

        driver.findElement(By.linkText("preferences")).click();

        System.out.println("founder header");
        WebElement headerLeft = header.findElement(By.id("header-bottom-left"));
        System.out.println("founder headerleft");
        WebElement ul = headerLeft.findElement(By.className("tabmenu "));
        System.out.println("founder ul");
        List<WebElement> tabs = ul.findElements(By.className("choice"));
        System.out.println("founder li and made list");
        System.out.println("List size: " + tabs.size());

        for(WebElement w : tabs) System.out.println(w.getText());
    }
}
