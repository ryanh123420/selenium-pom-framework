package com.ryanh.tests;

import com.ryanh.base.BaseTest;
import com.ryanh.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test scripts for the webapp homepage: https://wowutils.com/viserio-cooldowns
 */
public class HomeTests extends BaseTest {

    /**
     * Navigate to the Overview page for setting up assignments and notes
     * URL: https://wowutils.com/viserio-cooldowns/raid/overview
     */
    @Test
    public void NavigateToOverview(){
        HomePage homePage = new HomePage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns");

        homePage.navigateToOverview();
        Assert.assertEquals(driver.getCurrentUrl(), "https://wowutils.com/viserio-cooldowns/raid/overview");
    }
}
