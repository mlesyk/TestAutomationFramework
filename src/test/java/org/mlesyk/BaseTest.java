package org.mlesyk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.mlesyk.pages.HomePage;
import org.mlesyk.util.Browser;
import org.mlesyk.util.PropertyLoader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Maks.
 */
public class BaseTest {
    protected final Logger log = LogManager.getLogger(getClass().getSimpleName());
    HomePage homePage;
    Browser browser;

    TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            makeScreenshotOnFailure();
        }
    };

    ExternalResource browserRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            browser = new Browser();
            homePage = new HomePage(browser);
            homePage.open();
        }

        @Override
        protected void after() {
            browser.close();
        }
    };

    @Rule
    public final TestName testName = new TestName();

    @Rule
    public RuleChain rules = RuleChain
            .outerRule(browserRule)
            .around(screenshotOnFailure);

    private void makeScreenshotOnFailure() {
        byte[] screenshot = ((TakesScreenshot) browser.getDriver()).getScreenshotAs(OutputType.BYTES);
        String screenshotPath = PropertyLoader.getProperty("screenshot.path");
        String screenshotName = testName.getMethodName() + "_" + System.currentTimeMillis() + ".png";
        try (FileOutputStream fos = new FileOutputStream(screenshotPath + screenshotName)) {
            fos.write(screenshot);
            log.info("Screenshot {} created.", screenshotName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
