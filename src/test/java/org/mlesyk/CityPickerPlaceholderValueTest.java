package org.mlesyk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mlesyk.pages.modules.CityPickerPopUpModule;

/**
 * Created by Maks.
 */
public class CityPickerPlaceholderValueTest extends BaseTest {

    CityPickerPopUpModule cityPickerPopUpModule;

    @Test
    public void citySearchPlaceholderTest() {
        Assert.assertEquals("Начните вводить название", cityPickerPopUpModule.getCitySearchTextBoxPlaceholder());
    }

    @Before
    public void init() {
        cityPickerPopUpModule = homePage.invokeCityPickerPopUpModule();
    }
}
