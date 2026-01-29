package com.demontoast.pages;

import com.demontoast.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By assignmentsPageButton = By.cssSelector("#tools a.sm\\:flex");
    private final By setupsPageButton = By.cssSelector("#tools a[href*='setups']");
    private final By battleNetLogin = By.xpath("//img[@alt='Battle.net']/ancestor::button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOverview() {
        click(assignmentsPageButton);
    }

    public void navigateToSetups() {
        click(setupsPageButton);
    }

    public void navigateToLogin() {
        click(battleNetLogin);
    }
}
