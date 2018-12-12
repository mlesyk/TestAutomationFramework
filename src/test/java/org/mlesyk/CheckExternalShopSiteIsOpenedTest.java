package org.mlesyk;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.model.Offer;
import org.mlesyk.pages.ExternalShopPage;
import org.mlesyk.pages.ProductMainPage;
import org.mlesyk.pages.modules.ProductPageOffersTabModule;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maks.
 */
public class CheckExternalShopSiteIsOpenedTest extends BaseTest {

    private Map<String, String> shops = new HashMap<>();
    {
        shops.put("ExpoFree", "expofree.com.ua");
        shops.put("ORIGI", "origi.com.ua");
        shops.put("BigMag", "bigmag.ua");
        shops.put("Sota", "сота.укр");
        shops.put("MACLOVE", "maclove.ua");
        shops.put("iSPACE", "ispace.com.ua");
        shops.put("AppStore", "appstore.org.ua");
    }

    @Test
    public void externalShopSiteIsOpenedTest() {
        int leastComments = 10;
        int leastWarrantyMonths = 6;
        Pattern urlDomainNamePattern = Pattern.compile("(?:http(?:s?):\\/\\/)([^\\/\\r\\n]+)(?:\\/[^\\r\\n]*)?");

        ProductMainPage productMainPage = homePage.closeRegionPopUpIfActive()
                .typeSearchString("Apple iPhone 7")
                .pickItemFromSearchResultDropDownList("Apple iPhone 7 Plus 128GB Gold (MN4Q2)");

        ProductPageOffersTabModule offersTab = productMainPage.goToProductAllOffersModule();
        Offer offerMinPrice = offersTab.getOfferWithMinPrice(leastComments,leastWarrantyMonths);
        ExternalShopPage externalShopPage = offersTab.clickGoToShopButton(offerMinPrice);

        String shopUrl = externalShopPage.getShopUrl();
        Matcher urlMatcher = urlDomainNamePattern.matcher(shopUrl);

        Assert.assertTrue(urlMatcher.matches());
        Assert.assertEquals(urlMatcher.group(1), shops.get(externalShopPage.getShopName()));
    }

}
