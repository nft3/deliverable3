package com.testing;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * @author nickcarone
 *
 * As a Reddit user
 * I want to enter the wrong username/password combinations
 * so that I can ensure account safety
 *
 */
public class RedditSecurityTester {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.reddit.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testWrondUsernameAndPassword(){
        String text = "";
        try {
            driver.get(baseUrl + "/");
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).clear();
            driver.findElement(By.name("user")).sendKeys("kjlkj");
            driver.findElement(By.name("passwd")).clear();
            driver.findElement(By.name("passwd")).sendKeys("ljlxd");
            driver.findElement(By.cssSelector("button.btn")).click();
            text = driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.listing-page.hot-page." +
                    "front-page div.side div.spacer form#login_login-main.login-form.login-form-side div.status.error")).getText();
        } catch(NoSuchElementException e){
            fail();
        }

        assertEquals(text, "wrong password");
    }

    @Test
    public void testWrongUsername(){
        String text = "";
        try {
            driver.get(baseUrl + "/");
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).clear();
            driver.findElement(By.name("user")).sendKeys("dfgfdgdfgsdd");
            driver.findElement(By.name("passwd")).clear();
            driver.findElement(By.name("passwd")).sendKeys("password");
            driver.findElement(By.cssSelector("button.btn")).click();
            text = driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.listing-page.hot-page." +
                    "front-page div.side div.spacer form#login_login-main.login-form.login-form-side div.status.error")).getText();
        } catch(NoSuchElementException e){
            fail();
        }

        assertEquals(text, "wrong password");
    }

    @Test
    public void testWrongPassword(){
        String text = "";
        try {
            driver.get(baseUrl + "/");
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).clear();
            driver.findElement(By.name("user")).sendKeys("cs1632testdumby");
            driver.findElement(By.name("passwd")).clear();
            driver.findElement(By.name("passwd")).sendKeys("gsfdsfsdf");
            driver.findElement(By.cssSelector("button.btn")).click();
            text = driver.findElement(By.cssSelector("html.js.cssanimations.csstransforms body.listing-page.hot-page." +
                    "front-page div.side div.spacer form#login_login-main.login-form.login-form-side div.status.error")).getText();
        } catch(NoSuchElementException e){
            fail();
        }

        assertEquals(text, "wrong password");
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

