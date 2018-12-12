package org.mlesyk.pages;

import org.mlesyk.pages.modules.CityPickerPopUpModule;
import org.mlesyk.util.Browser;
import org.mlesyk.util.PropertyLoader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Maks.
 */
public class HomePage extends BasePage {
    private static final String BASE_URL = PropertyLoader.getProperty("baseUrl");

    @FindBy(id = "searchbox")
    WebElement searchTextBox;

    @FindBy(className = "city-link")
    WebElement cityPicker;

    @FindBy(xpath = "//div[@id='live-search']//a[@class='ui-menu-item-wrapper']")
    List<WebElement> searchResultLinksDropDown;

    @FindBy(xpath = "//div[@class='confirm-city dropdown-bd active']/i[@class='close']")
    WebElement regionPopUpCloseButton;

    @FindBy(xpath = "//div[@class='confirm-city dropdown-bd active']")
    List<WebElement> regionPopUp;

    public HomePage(Browser browser) {
        super(browser);
    }

    public HomePage open() {
        driver.get(BASE_URL);
        log.info(BASE_URL + " opened");
        return this;
    }

    public HomePage typeSearchString(String item) {
        browser.getWait()
                .until(ExpectedConditions.elementToBeClickable(searchTextBox))
                .sendKeys(item);
        log.info("'{}' is entered in search box", item);
        return this;
    }

    public ProductMainPage pickItemFromSearchResultDropDownList(String item) {
        browser.getWait()
                .until(ExpectedConditions.visibilityOfAllElements(searchResultLinksDropDown));
        searchResultLinksDropDown.stream()
                .filter(e -> e.getText().contains(item))
                .findFirst()
                .get()
                .click();
        log.info("Opening first found item from search results containing {}", item);
        return new ProductMainPage(browser);
    }

    public CityPickerPopUpModule invokeCityPickerPopUpModule() {
        browser.getWait()
                .until(ExpectedConditions.elementToBeClickable(cityPicker))
                .click();
        log.info("City picker is opened");
        return new CityPickerPopUpModule(browser);
    }

    public HomePage closeRegionPopUpIfActive() {
        if (regionPopUp.size() > 0) {
            log.info("Closing 'Region' popup");
            browser.getWait()
                    .until(ExpectedConditions.elementToBeClickable(regionPopUpCloseButton))
                    .click();
        }
        return this;
    }

}
