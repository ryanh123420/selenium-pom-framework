package com.ryanh.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * A BasePage class for other Page classes to extend. Contains methods such as clicking buttons
 * and typing characters that are wrapped with waiting strategies.
 * TODO Add more complex actions with Actions API
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    private final By acceptCookies = By.cssSelector("div.cky-consent-container button.cky-btn-accept");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Waits until the element is visible on the page.
     * @param element By locator for an element
     * @return Wait strategy for the element to be visible
     */
    protected WebElement waitUntilVisible(By element) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    /**
     * Waits until the element is clickable on the page.
     * @param element By locator for an element
     * @return Wait strategy for the element to be clickable
     */
    protected WebElement waitUntilClickable(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until the element exists in the DoM.
     * @param element By locator for an element
     */
    protected void waitUntilExists (By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    /**
     * Wait for the page URL to be the parameter String
     * @param url String URL
     */
    protected void waitForPageURL(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Waits for an element to become stale, useful for forced page navigation
     * @param e
     */
    protected void waitForStaleElement(WebElement e) {
        wait.until(ExpectedConditions.stalenessOf(e));
    }

    /**
     * Calls the WebElement.click() method, wrapped with a waiting strategy
     * @param element By locator for an element
     */
    protected void click(By element) {
        waitUntilClickable(element).click();
    }

    /**
     * Calls the WebElement.sendKeys() method, wrapped with a waiting strategy
     * @param element By locator for an element
     */
    protected void type(By element, String text) {
        waitUntilVisible(element).sendKeys(text);
    }

    /**
     * Accepts the cookies popup when first logging in
     */
    protected void acceptCookies() {
        wait.until(ExpectedConditions.presenceOfElementLocated(acceptCookies));
        click(acceptCookies);
    }
}
