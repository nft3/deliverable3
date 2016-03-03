package com.testing;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @Author Nicholas Treu and Nicholas Carone
 *
 * As a user of Reddit.com
 * I would like to be able to search for subreddits
 * so that I can explore the world of reddit more
 */
public class RedditSearchTester {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.reddit.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /*
       Given that I like cats
       When I am on the internet
       Then I want to search for a subreddit about cats
     */

    @Test
    public void searchCatSubReddit() {
        try{
            driver.get(baseUrl + "/");
            driver.findElement(By.name("q")).click();
            driver.findElement(By.name("q")).click();
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys("cats");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
            driver.findElement(By.cssSelector("mark")).click();
        } catch(NoSuchElementException e){
            fail();
        }

        assertNotEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test

    /*
       Given that I like hockey
       When I am on the internet
       Then I want to search for a subreddit about the Pittsburgh Penguins
     */
    public void searchPittsburghPenguinsSubReddit() {
        try{
            driver.get(baseUrl + "/");
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys("pittsburgh penguins");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
            driver.findElement(By.cssSelector("div.listing.search-result-listing")).click();
            driver.findElement(By.linkText("Pittsburgh Penguins News and Topics")).click();
        } catch(NoSuchElementException e){
            fail();
        }

        assertNotEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test

    /*
       Given that I like football
       When I am on the internet
       Then I want to search for a subreddit about the Pittsburgh Steelers
     */
    public void searchPittsburghSteelersSubReddit(){
        try{
            driver.get(baseUrl + "/");
            driver.findElement(By.name("q")).click();
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys("pittsburgh steelers");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
            driver.findElement(By.linkText("Pittsburgh Steelers news and topics")).click();
        } catch (NoSuchElementException e){
            fail();
        }

        assertNotEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test

    /*
       Given that I am on a subreddit,
       When I want to go back to the homepage
       Then I click the back button twice

       NOTE: The other tests of this format work perfectly, but this one is expecting "//" for some
       reason at the end of the baseURL
     */
    public void testBackButton(){
        try{
            driver.get(baseUrl);
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys("pittsburgh steelers");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
            driver.findElement(By.linkText("Pittsburgh Steelers news and topics")).click();
            driver.navigate().back();
            driver.navigate().back();

        } catch (NoSuchElementException e){
            fail();
        }

        assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test
    public void testInvalidLogin() throws NoSuchElementException {
        driver.get(baseUrl + "/");
        driver.findElement(By.name("user")).click();
        driver.findElement(By.name("user")).clear();
        driver.findElement(By.name("user")).sendKeys("kjlkj");
        driver.findElement(By.name("passwd")).clear();
        driver.findElement(By.name("passwd")).sendKeys("ljlxd");
        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.listing-page.hot-page.front-page div.side div.spacer form#login_login-main.login-form.login-form-side div.status.error"));
    }

    @Test

    /*
       Given that I am on a subreddit,
       When I want to go back to the homepage
       Then I want to go back to the subreddit page I was on back click the forward button twice
     */
    public void testBackandForwardButton(){
        try{
            driver.get(baseUrl + "/");
            driver.findElement(By.name("q")).click();
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys("pittsburgh steelers");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
            driver.findElement(By.linkText("Pittsburgh Steelers news and topics")).click();
            driver.navigate().back();
            driver.navigate().back();
            driver.navigate().forward();
            driver.navigate().forward();
        } catch (NoSuchElementException e){
            fail();
        }

        assertNotEquals(driver.getCurrentUrl(), baseUrl);
    }
    @Test
    //Given that I am on the home screen
    //When I search for 'games'
    //Then I want to view search results for games
    public void testGamesSearch() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.name("q")).click();
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("games");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("https://www.reddit.com/search?q=games", driver.getCurrentUrl());
    }
    @Test
    //Given that I am on the home screen
    //When I search for 'dogs'
    //Then I want to view search results for dogs
    public void testDogsSearch() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("dogs");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("https://www.reddit.com/search?q=dogs", driver.getCurrentUrl());
    }
    @Test
    //Given that I am on the home screen
    //When I search for sports
    //Then I want to view search results for sports
    public void testSportsSearch() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.name("q")).click();
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("sports");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("https://www.reddit.com/search?q=sports", driver.getCurrentUrl());
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
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
