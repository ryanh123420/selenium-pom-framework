package com.demontoast.pages;

import com.demontoast.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("accountName");
    private final By loginButton = By.id("submit");
    private final By passwordField = By.id("password");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        click(loginButton);
        type(passwordField, password);
        click(loginButton);
    }

    public boolean isPageOpened() {
        return driver.getCurrentUrl().contains("account.battle.net/login");
    }
}