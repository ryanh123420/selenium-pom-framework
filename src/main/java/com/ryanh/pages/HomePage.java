package com.ryanh.pages;

import com.ryanh.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By assignmentsPageButton = By.xpath("//span[contains(text(), 'Cooldowns Hub')]");
    private final By battleNetLogin = By.xpath("//img[@alt='Battle.net']/ancestor::button");
    private final String pageURL = "https://wowutils.com/viserio-cooldowns";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOverview() {
        click(assignmentsPageButton);
        waitForPageURL("https://wowutils.com/viserio-cooldowns/raid/overview");
    }

    public void navigateToLogin() {
        click(battleNetLogin);
    }
}
