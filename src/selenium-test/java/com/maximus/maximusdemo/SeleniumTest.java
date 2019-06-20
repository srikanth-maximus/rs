package com.maximus.tc.selenium;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTest {
    
    WebDriver driver;
    String baseURL;
    String hubURL;
    
    @Before
    public void setUp() throws MalformedURLException {
        baseURL = System.getProperty("testUrl");
        hubURL = System.getProperty("seleniumUrl");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.LINUX);
        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
    }
    
    @After
    public void afterTest() {
        driver.quit();
    }
    
    @Test
    public void test() {
        driver.get(baseURL);
        assertTrue(driver.getPageSource().contains("Hello World!"));
    }
}
