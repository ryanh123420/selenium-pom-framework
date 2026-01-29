package com.demontoast.utils;

import com.demontoast.pages.HomePage;
import com.demontoast.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class RunWithoutTests {
    public static void main(String[] args) {
        WebDriver driver = DriverFactory.createDriver(DriverFactory.BrowserTypes.CHROME);

        driver.get("https://wowutils.com/viserio-cooldowns");

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.navigateToLogin();
        loginPage.login(System.getenv("BATTLENET_EMAIL_TEST"), System.getenv("BATTLENET_PASSWORD_TEST"));


    }
}
