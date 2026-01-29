package com.demontoast.pages;

import com.demontoast.base.BasePage;
import org.openqa.selenium.WebDriver;

public class OverviewPage extends BasePage {

    public OverviewPage(WebDriver driver){
        super(driver);
    }

    public boolean isPageOpened() {
        return driver.getCurrentUrl().contains("wowutils.com/viserio-cooldowns/raid/overview");
    }
}
