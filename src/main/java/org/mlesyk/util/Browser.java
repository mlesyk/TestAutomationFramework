package org.mlesyk.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Maks.
 */
public class Browser {

    private String browserName = PropertyLoader.getProperty("browser");
    private Boolean isGridActive = Boolean.parseBoolean(PropertyLoader.getProperty("grid.active"));
    private WebDriver driver;
    private WebDriverWait wait;

    public Browser() {
        driver = DriverFactory.getDriver(browserName, isGridActive);
        int implicitWaitTimeout = Integer.parseInt(PropertyLoader.getProperty("implicitWaitTimeoutSeconds"));
        driver.manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        driver.quit();
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, Integer.parseInt(PropertyLoader.getProperty("explicitWaitTimeoutSeconds")));
        }
        return wait;
    }

    public void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);
    }
}
