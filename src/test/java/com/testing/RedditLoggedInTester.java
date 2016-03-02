package com.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Nicholas Treu
 * As a logged in user of reddit.com
 * I want to have changed attributes of my proile
 * So that I can personalize it for my needs.
 */
public class RedditLoggedInTester {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String baseTitle;

    @Before
    /*
        We are setting up to a logged in profile to test for. Every time we want a test we have to log into the profile.
     */
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.reddit.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/");
        baseTitle = driver.getTitle();
        driver.findElement(By.name("user")).click();
        driver.findElement(By.name("user")).clear();
        driver.findElement(By.name("user")).sendKeys("cs1632testdumby");
        driver.findElement(By.name("passwd")).clear();
        driver.findElement(By.name("passwd")).sendKeys("password");
        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    /*
        Given that I am logged into Reddit
        Then I want to change my preferences so that
        When I click a link, it will open in a new tab

        NOTE: This fails because it doesn't get the path for the "preferences" link.
         I talked to Bill about this and we both couldn't find out what is wrong. He said to
         make a note of it here that it would work otherwise that we couldn't figure out the issues togeter.
    */
    public void testOpenWindowInNewTab() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.listing-page.with-listing-chooser." +
                "loggedin.front-page.hot-page div#header div#header-bottom-right ul.flat-list.hover li a.pref-lang.choice")).click();
        driver.findElement(By.id("newwindow")).click();
        driver.findElement(By.cssSelector("input.btn")).click();
        driver.findElement(By.id("header-img")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/div[2]/p[1]/a")).click();

        // Switch to the new tab
        String currentTab = driver.getWindowHandle();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        tabs.remove(currentTab);
        driver.switchTo().window(tabs.get(0));

        // Assert that the title of the new window are now in is NOT equal to the original tab we opened.
        assertNotEquals(baseTitle, driver.getTitle());
    }

    @Test
    /*
        Given that I am logged into Reddit.com
        Then I want to access all the preference tabs
        When I want to make changes to my profile.
     */
    public void testLoggedInTabs(){

        String[] urls = {"https://www.reddit.com/prefs/", "https://www.reddit.com/prefs/apps/",
                "https://www.reddit.com/prefs/feeds/", "https://www.reddit.com/prefs/friends/",
                "https://www.reddit.com/prefs/blocked/", "https://www.reddit.com/prefs/update/",
                "https://www.reddit.com/prefs/delete/"};


        // Test that we are on the current url and that the button clicks work. If we aren't then fail.
        try {

            driver.findElement(By.id("header-bottom-left")).click();

            driver.findElement(By.linkText("preferences")).click();
            if(!getCurrentURL().equals(urls[0]))
                fail();

            driver.findElement(By.linkText("apps")).click();
            if(!getCurrentURL().equals(urls[1]))
                fail();

            driver.findElement(By.linkText("RSS feeds")).click();
            if(!getCurrentURL().equals(urls[2]))
                fail();

            driver.findElement(By.linkText("friends")).click();
            if(!getCurrentURL().equals(urls[3]))
                fail();

            driver.findElement(By.linkText("blocked")).click();
            if(!getCurrentURL().equals(urls[4]))
                fail();

            driver.findElement(By.linkText("password/email")).click();
            if(!getCurrentURL().equals(urls[5]))
                fail();

            driver.findElement(By.linkText("delete")).click();
            if(!getCurrentURL().equals(urls[6]))
                fail();

        }
        // If we ever encounter a NoSuchElementException, fail.
        catch (NoSuchElementException e){
            fail();
        }

        // If we get to this point, then everything passed and work properly
        assertTrue(true);
    }

    @Test
    /*
        Given that I am logged in to Reddit.com
        Then I want to explore App documentation
        When I click the click to the Reddit API Access.
     */
    public void testAppDeveloperAPILink() {
        try {
            driver.findElement(By.linkText("preferences")).click();
            driver.findElement(By.linkText("apps")).click();
            driver.findElement(By.id("create-app-button")).click();
            driver.findElement(By.linkText("read the API usage guidelines")).click();
            assertEquals(getCurrentURL(), "https://www.reddit.com/wiki/api");
        } catch (NoSuchElementException e){
            fail();
        }
    }

    @Test
    /*
        Given that I am logged in to Reddit.com
        Then I want to be able to log out when I am doing using it
        When I click on the logout button.

        NOTE: this test works because it does not have to access the link for "preferences".
     */
    public void testLogout(){
        try {
            driver.findElement(By.linkText("logout")).click();
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("passwd")).click();
            driver.findElement(By.id("rem-login-main")).click();
        } catch(NoSuchElementException e){
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
