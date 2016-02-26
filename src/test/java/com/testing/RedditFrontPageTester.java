package com.testing;

import static org.junit.Assert.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * As a user of Reddit.com
 * I would like to use functionality on the front page of the site
 * So that I can see what other's have to say.
 *
 * @author Nicholas Treu
 */
public class RedditFrontPageTester {

    WebDriver driver;

    @Before
    // Instantiate a new FirefoxDriver object and go to the Reddit homepage
    public void setup() throws Exception {
        driver = new FirefoxDriver();
        driver.get("https://www.reddit.com");
    }


    @After
    // Close any connections that we have opened doing our tests.
    public void tearDown() {
        driver.close();
    }

    private String getFrontPageTitle(){
        return "reddit: the front page of the internet";
    }

    @Test
    /*
        Given that I am on the front page of Reddit
        When I view the title on the top of the browser
        Then I want to see that it is:
         "reddit: the front page of the internet"
     */
    public void testRedditTitle() {
        // assert equals
        assertEquals(getFrontPageTitle(), driver.getTitle());
    }

    @Test
    /*
        Given that I am on the front page of Reddit
        Then I want to see if any of the links are about cats.
     */

    public void testForHeaderLinks(){
        // Check that there are any links about cats on the page
        try{
            driver.findElement(By.linkText("controversial"));
            driver.findElement(By.linkText("wiki"));
            driver.findElement(By.linkText("promoted"));
        } catch(NoSuchElementException e){
            fail();
        }
    }

    @Test
    /*
        Given that I am on the front page of Reddit.com
        Then I want to see if a valid username/password works
        When I log in
     */

    public void testValidLogin(){

        // Get the username and password elements from the page
        WebElement username = driver.findElement(By.name("user"));
        WebElement password = driver.findElement(By.name("passwd"));

        // Send keys of a test dumby account created for this assignment
        username.sendKeys("cs1632testdumby");
        password.sendKeys("password");

        // Push the login button
        WebElement login = driver.findElement(By.id("login_login-main"));
        login.findElement(By.className("btn")).click();

        try{
            WebElement header = driver.findElement(By.id("header"));
            System.out.println("header passed");
            WebElement headerRight = header.findElement(By.id("header-bottom-right"));
            System.out.println("headerRight passed");
            WebElement logout = driver.findElement(By.cssSelector("form.logout.hover"));
            System.out.println("logout passed");
            assertTrue(logout.isDisplayed());
        } catch (NoSuchElementException e){
            fail();
        }
    }

    @Test
    /*
        Given that I am on the front page of Reddit
        Then I want to go rising section
        When I click on "rising" in the header bar.
     */
    public void testHeadButtonClick(){
        WebElement newSection = driver.findElement(By.linkText("rising"));
        newSection.click();
        assertEquals(driver.getTitle(), "rising submissions : reddit.com");
    }

    @Test
    /*
        Given that I start out on the front Reddit page
        Then I want to navigate to "promoted" then back to the front page
        When I click on "promoted" and click on the back button
     */
    public void testBackButton(){
        WebElement buttonClick = driver.findElement(By.linkText("promoted"));
        buttonClick.click();
        if(driver.getTitle().equals("promoted links : reddit.com")){
            driver.navigate().back();
            assertEquals(getFrontPageTitle(), driver.getTitle());
        }
        else {
            fail();
        }
    }

    @Test
    /*
        Given that I start out on the front Reddit Page
        Then I want to navigate to "wiki" then back to the front page then back to "wiki" again
        When I click on "wiki" and click back and then click forward
     */

    public void testForwardButton(){
        WebElement buttonClick = driver.findElement(By.linkText("wiki"));
        buttonClick.click();
        System.out.println(driver.getTitle());
        String wikiTitle = driver.getTitle();
        if(driver.getTitle().equals("index - reddit.com")){
            driver.navigate().back();
            if(getFrontPageTitle().equals(driver.getTitle())){
                driver.navigate().forward();
                assertEquals(wikiTitle, driver.getTitle());
            }
        }
        else {
            fail();
        }
    }
}