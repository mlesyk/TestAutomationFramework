package org.mlesyk.pages.modules;

import org.mlesyk.pages.BasePage;
import org.mlesyk.util.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maks.
 */
public class CityPickerPopUpModule extends BasePage {
    @FindBy(className = "ui-menu-item")
    List<WebElement> citySearchSuggestedItems;

    @FindBy(name = "autocomplete-city")
    WebElement citySearchTextBox;

    @FindBy(xpath = "//div[@class='lightbox-hd']")
    WebElement cityPickerPopUpHeader;

    @FindBy(xpath =  "//div[@class='lightbox-bd']")
    WebElement getCityPickerPopUpBody;

    @FindBy(xpath = "//div[@class='lightbox']/i[@class='close']")
    WebElement cityPickerPopUpCloseButton;

    public CityPickerPopUpModule(Browser browser) {
        super(browser);
    }

    public List<String> getCitySearchSuggestedItems() {
        return citySearchSuggestedItems.stream().map(c -> c.getText()).collect(Collectors.toList());
    }

    public String getCitySearchTextBoxPlaceholder() {
        return citySearchTextBox.getAttribute("placeholder");
    }

    public CityPickerPopUpModule enterCitySearchCriteria(String query) {
        citySearchTextBox.sendKeys(query);
        return this;
    }

    public boolean isPickerPopUpHeaderVisible() {
        return cityPickerPopUpHeader.isDisplayed();
    }

    public boolean isPickerPopUpBodyVisible() {
        return getCityPickerPopUpBody.isDisplayed();
    }

    public void closeCityPickerPopup() {
        if (cityPickerPopUpCloseButton.isDisplayed()) {
            browser.getWait()
                    .until(ExpectedConditions.elementToBeClickable(cityPickerPopUpCloseButton))
                    .click();
            log.info("Closed city picker popup");
        }
    }
}
