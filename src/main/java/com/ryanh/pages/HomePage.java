package com.ryanh.pages;

import com.ryanh.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By assignmentsPageButton = By.cssSelector("#tools a.sm\\:flex");
    private final By setupsPageButton = By.cssSelector("#tools a[href*='setups']");
    private final By battleNetLogin = By.xpath("//img[@alt='Battle.net']/ancestor::button");
    String pageURL = "https://wowutils.com/viserio-cooldowns";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOverview() {
        click(assignmentsPageButton);
        waitForPageURL("https://wowutils.com/viserio-cooldowns/raid/overview");
    }

    public void navigateToSetups() {
        click(setupsPageButton);
    }

    public void navigateToLogin() {
        click(battleNetLogin);
    }

    public void waitForPageLoad() {
        waitForPageURL(pageURL);
    }
}
