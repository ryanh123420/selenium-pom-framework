package com.ryanh.pages;

import com.ryanh.base.BasePage;
import com.ryanh.components.BossCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Page where users can see an overview of all created cooldown and assignment notes.
 * TODO Add additional functionality and locators for changing difficulty, season, personal vs group, etc.
 */
public class OverviewPage extends BasePage {
    //Root element for "BossCard" components found on the overview page.
    private final By bossCards = By.cssSelector("div.flex div.grid div.border:not(.items-center)");

    public OverviewPage(WebDriver driver){
        super(driver);
    }

    /**
     * Calls the wrapped wait method in BasePage to wait for the URL to match the overview page URL
     */
    public void waitForPageLoad() {
        String pageURL = "https://wowutils.com/viserio-cooldowns/raid/overview";
        waitForPageURL(pageURL);
    }

    /**
     * Get all BossCard objects on the Overview Page. Find all the root elements for each BossCard, then map those
     * roots to new BossCard objects, then convert to a List of BossCards.
     * @return - List of BossCard objects.
     */
    public List<BossCard> getBossCards() {
        return driver.findElements(bossCards).stream()
                .map(root -> new BossCard(driver,root))
                .toList();
    }

    /**
     * Find a specific BossCard from a list of BossCards. Use a stream to filter a specific boss from the list, then
     * find the first card since there should never be a duplicate. If no cards are found, throw an error.
     * @param bossName - The name of a specific boss.
     * @return - A specific BossCard.
     */
    public BossCard getBossByName(String bossName) {
        return getBossCards().stream()
                .filter(b -> b.getBossName().equals(bossName))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Card not found: " + bossName)
                );
    }
}
