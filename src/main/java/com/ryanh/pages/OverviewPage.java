package com.ryanh.pages;

import com.ryanh.base.BasePage;
import com.ryanh.components.BossCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.NoSuchElementException;

public class OverviewPage extends BasePage {
    //Root element for "BossCard" components found on the overview page
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
     * Get all BossCard objects on the Overview Page, first we find the bossCards selector which is a List of the root
     * elements for each card, then map those roots to new BossCard objects, then add them to a List of BossCards.
     * @return - List of BossCard objects.
     */
    public List<BossCard> getBossCards() {
        return driver.findElements(bossCards).stream()
                .map(root -> new BossCard(driver,root))
                .toList();
    }

    /**
     * Get a BossCard by a boss name by first calling getBossCards(), then using a stream we filter a BossCard from the
     * list using the getBossName() method and comparing it to the bossName parameter. Then we findFirst() from the
     * filter, since we should never have a duplicate card, and if no cards are found, throw an error.
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
