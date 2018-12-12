package org.mlesyk.pages;

import org.mlesyk.pages.modules.ProductPageOffersTabModule;
import org.mlesyk.util.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Maks.
 */
public class ProductMainPage extends BasePage {
    @FindBy(css = "span.pointer")
    WebElement allOffersLink;

    @FindBy(xpath = "//*[@class='tabs-scroll-app']/*[@data-id='prices']")
    WebElement allOffersMenuTab;

    public ProductMainPage(Browser browser) {
        super(browser);
    }

    public ProductPageOffersTabModule goToProductAllOffersModule() {
        browser.getWait()
                .until(ExpectedConditions.elementToBeClickable(allOffersLink))
                .click();
        log.info("Clicked on 'All offers' link");
        return new ProductPageOffersTabModule(browser);
    }

    public boolean isAllOffersMenuTabActive() {
        return allOffersMenuTab.getAttribute("class").equals("active");
    }

}
