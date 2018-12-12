package org.mlesyk.pages.modules;

import org.mlesyk.model.Offer;
import org.mlesyk.pages.BasePage;
import org.mlesyk.pages.ExternalShopPage;
import org.mlesyk.util.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Maks.
 */
public class ProductPageOffersTabModule extends BasePage {

    @FindBy(xpath = "//div[@data-selector='price-line']")
    List<WebElement> offersWebElementList;

    List<Offer> offersEntities;
    ProductPageSingleOfferModule offersModule = new ProductPageSingleOfferModule(browser);

    public ProductPageOffersTabModule(Browser browser) {
        super(browser);
    }

    private void initOffersEntities() {
        browser.getWait().until(ExpectedConditions.visibilityOfAllElements(offersWebElementList));
        this.offersEntities = new ArrayList<Offer>();
        long startTimestamp = System.currentTimeMillis();
        for (WebElement offerWebElement : offersWebElementList) {
            Offer offer = new Offer();
            offer.setShopName(offersModule.getShopName(offerWebElement));
            offer.setCommentsAmount(offersModule.getCommentsAmount(offerWebElement));
            offer.setWarranty(offersModule.getWarranty(offerWebElement));
            offer.setPrice(offersModule.getPrice(offerWebElement));
            offer.setGoToShopButton(offersModule.getGoToShopButton(offerWebElement));
            offersEntities.add(offer);
        }
        long endTimestamp = System.currentTimeMillis();
        log.debug("Offers initialized in {} ms", (endTimestamp - startTimestamp));
    }

    public Offer getOfferWithMinPrice(int leastComments, int leastWarranty) {
        initOffersEntities();
        long startTimestamp = System.currentTimeMillis();
        Offer minPriceOffer = offersEntities.stream()
                .filter(offer -> offer.getCommentsAmount() >= leastComments)
                .filter(offer -> offer.getWarranty() >= leastWarranty)
                .min(Comparator.comparing(Offer::getPrice))
                .get();
        long endTimestamp = System.currentTimeMillis();
        log.debug("Offer with minimum price found in {} ms", (endTimestamp - startTimestamp));
        log.debug("Offer with minimum price is:\n {} ", minPriceOffer);
        return minPriceOffer;
    }

    public ExternalShopPage clickGoToShopButton(Offer offer) {
        int windowsCount = driver.getWindowHandles().size();
        offer.getGoToShopButton().click();
        log.debug("'Go to shop' button clicked for offer {}", offer.getShopName());
        // !!! Exceptional case for Thread.sleep() usage !!!
        // https://github.com/SeleniumHQ/selenium/issues/6583
        // driver.getWindowHandles() is not updating immediately when new tab is opened via click()
        // should wait for couple second
        try {
            while(driver.getWindowHandles().size() == windowsCount) {
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            log.error(e);
        }
        //switch to new tab
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        return new ExternalShopPage(offer.getShopName(), browser);
    }

    public boolean offersExist() {
        browser.getWait().until(ExpectedConditions.visibilityOfAllElements(offersWebElementList));
        return offersWebElementList.size() > 0;
    }
}
