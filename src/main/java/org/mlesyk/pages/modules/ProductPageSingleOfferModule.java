package org.mlesyk.pages.modules;

import org.mlesyk.pages.BasePage;
import org.mlesyk.util.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Maks.
 */
public class ProductPageSingleOfferModule extends BasePage {
    By shopName = By.xpath(".//div[@class='ellipsis']");
    By commentsLink = By.xpath(".//div[@class='ellipsis']/following-sibling::div//a[@data-tracking-id='offer-1']");
    By warranty = By.xpath(".//span[@class='app-box']/parent::div");
    By price = By.xpath(".//a[@class='price-lg']");
    By goToShopButton = By.xpath(".//div[@class='cell-7 cell-md']/a[@class='btn-orange btn-cell']");

    public ProductPageSingleOfferModule(Browser browser) {
        super(browser);
    }

    public String getShopName(WebElement offerItem) {
        return offerItem.findElement(shopName).getText();
    }

    public Integer getCommentsAmount(WebElement offerItem) {
        List<WebElement> commentsAmount = offerItem.findElements(commentsLink);
        // check whether comments exist, if there are no comments - element is not present
        return commentsAmount.size() == 0 ? 0 : Integer.parseInt(commentsAmount.get(0).getText().replaceAll("[^0-9]", ""));
    }

    public Integer getWarranty(WebElement offerItem) {
        List<WebElement> warrantyAmount = offerItem.findElements(warranty);
        int warrantyMonths = 0;
        if(warrantyAmount.size() != 0) {
            try {
                warrantyMonths = Integer.parseInt(warrantyAmount.get(0).getText().replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                //ignore, there are no numbers
            }
        }
        return warrantyMonths;
    }

    public Double getPrice(WebElement offerItem) {
        return Double.parseDouble(offerItem.findElement(price).getText()
                .replaceAll("[^0-9,]", "")
                .replaceAll("\\s", "")
                .replace(",", "."));
    }

    public WebElement getGoToShopButton(WebElement offerItem) {
        return offerItem.findElement(goToShopButton);
    }

}
