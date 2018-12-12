package org.mlesyk;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.pages.modules.ProductPageOffersTabModule;
import org.mlesyk.pages.ProductMainPage;

/**
 * Created by Maks.
 */
public class ProductPageAllOffersTabTest extends BaseTest {

    @Test
    public void productPageAllOffersTabInvocationTest() {
        ProductMainPage productMainPage = homePage.closeRegionPopUpIfActive()
                .typeSearchString("Apple iPhone 7")
                .pickItemFromSearchResultDropDownList("Apple iPhone 7 Plus 128GB Gold (MN4Q2)");
        ProductPageOffersTabModule offersTab = productMainPage.goToProductAllOffersModule();

        Assert.assertTrue(productMainPage.isAllOffersMenuTabActive());
        Assert.assertTrue(offersTab.offersExist());
    }
}
