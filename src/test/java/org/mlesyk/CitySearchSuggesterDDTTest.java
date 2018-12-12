package org.mlesyk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mlesyk.pages.modules.CityPickerPopUpModule;
import org.mlesyk.util.ExcelReader;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Maks.
 */

@RunWith(Parameterized.class)
public class CitySearchSuggesterDDTTest extends BaseTest {

    CityPickerPopUpModule cityPickerPopUpModule;
    private static String testDataFile = "/" + CitySearchSuggesterDDTTest.class.getSimpleName() + ".xlsx";

    private String city;
    private String expectedCity;

    @Parameters(name = "{index}: Input={0}")
    public static Collection data() {
        return ExcelReader.readExcelData(testDataFile);
    }

    public CitySearchSuggesterDDTTest(String city, String expectedCity) {
        this.city = city;
        this.expectedCity = expectedCity;
    }

    @Test
    public void citySearchSuggestedItemEqualityTest() {
        List<String> suggestedCityItems = cityPickerPopUpModule.enterCitySearchCriteria(city)
                .getCitySearchSuggestedItems();
        Assert.assertEquals(suggestedCityItems.size(), 1);
        String suggestedCity = suggestedCityItems.get(0);
        assertThat(suggestedCity, equalTo(expectedCity));
    }

    @Before
    public void init() {
        cityPickerPopUpModule = homePage.invokeCityPickerPopUpModule();
    }
}
