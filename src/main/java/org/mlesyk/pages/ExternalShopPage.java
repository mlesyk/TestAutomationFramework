package org.mlesyk.pages;

import org.mlesyk.util.Browser;

/**
 * Created by Maks.
 */
public class ExternalShopPage extends BasePage {

    private String shopName;

    public ExternalShopPage(String shopName, Browser browser) {
        super(browser);
        this.shopName = shopName;
    }

    public String getShopUrl() {
        return driver.getCurrentUrl();
    }

    public String getShopName() {
        return shopName;
    }
}
