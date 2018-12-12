package org.mlesyk.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Created by Maks.
 */
public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class.getSimpleName());

    private static final String CHROME = "chrome";
    private static final String CHROME_HEADLESS = "chrome-headless";
    private static final String FIREFOX = "firefox";
    private static final String FIREFOX_HEADLESS = "firefox-headless";

    private DriverFactory() {
    }

    public static WebDriver getDriver(String browserName, boolean gridActive) {
        WebDriver driver = null;
        Capabilities capabilities;
        if (gridActive) {
            log.debug("Selenium grid is active");
            String hubUrl = PropertyLoader.getProperty("grid.hub.url");
            log.debug("Grid URL={}", hubUrl);
            switch (browserName) {
                case FIREFOX:
                    capabilities = new FirefoxOptions();
                    log.debug("Firefox capabilities: {}", capabilities.toString());
                    break;
                case FIREFOX_HEADLESS:
                    capabilities = new FirefoxOptions().setHeadless(true);
                    log.debug("Firefox capabilities: {}", capabilities.toString());
                    break;
                case CHROME:
                    capabilities = new ChromeOptions()
                            .addArguments("--disable-gpu")
                            .addArguments("--no-sandbox")
                            .addArguments("--disable-dev-shm-usage");
                    log.debug("Chrome capabilities: {}", capabilities.toString());
                    break;
                case CHROME_HEADLESS:
                    capabilities = new ChromeOptions()
                            .addArguments("--headless")
                            .addArguments("--disable-gpu")
                            .addArguments("--no-sandbox")
                            .addArguments("--disable-dev-shm-usage");
                    log.debug("Chrome capabilities: {}", capabilities.toString());

                    break;
                default:
                    capabilities = new FirefoxOptions().setHeadless(true);
                    break;
            }
            try {
                driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
                log.debug("Driver: {}", driver.toString());

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            switch (browserName) {
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case CHROME_HEADLESS:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    driver = new ChromeDriver(options);
                    break;
                case FIREFOX_HEADLESS:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                    break;
                default:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
            }
        }
        return driver;
    }
}
