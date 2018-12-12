package org.mlesyk.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlesyk.util.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Maks.
 */
public class BasePage {
    protected WebDriver driver;
    protected Browser browser;
    protected final Logger log = LogManager.getLogger(getClass().getSimpleName());

    public BasePage(Browser browser) {
        this.browser = browser;
        this.driver = browser.getDriver();
        PageFactory.initElements(driver, this);
    }
}
