package org.mlesyk;

import org.junit.Assert;
import org.junit.Test;
import org.mlesyk.pages.modules.CityPickerPopUpModule;

/**
 * Created by Maks.
 */
public class HomePageCityPickerInvocationTest extends BaseTest {

    @Test
    public void cityPickerPopUpInvocationTest() {
        CityPickerPopUpModule cityPickerPopUpModule = homePage.closeRegionPopUpIfActive()
                .invokeCityPickerPopUpModule();
        Assert.assertTrue(cityPickerPopUpModule.isPickerPopUpHeaderVisible());
        Assert.assertTrue(cityPickerPopUpModule.isPickerPopUpBodyVisible());

        cityPickerPopUpModule.closeCityPickerPopup();

        Assert.assertFalse(cityPickerPopUpModule.isPickerPopUpHeaderVisible());
        Assert.assertFalse(cityPickerPopUpModule.isPickerPopUpBodyVisible());
    }

}
