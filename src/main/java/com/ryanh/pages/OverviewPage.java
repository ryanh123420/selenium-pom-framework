package com.ryanh.pages;

import com.ryanh.base.BasePage;
import com.ryanh.components.BossCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class OverviewPage extends BasePage {
    private final By bossCards = By.cssSelector("div.flex div.grid div.border:not(.items-center)");

    public OverviewPage(WebDriver driver){
        super(driver);
    }

    public void waitForPageLoad() {
        String pageURL = "https://wowutils.com/viserio-cooldowns/raid/overview";
        waitForPageURL(pageURL);
    }

    /**
     * Get all BossCard objects on the Overview Page
     * @return List of BossCard objects
     */
    public List<BossCard> getBossCards() {
        return driver.findElements(bossCards).stream()
                .map(root -> new BossCard(driver,root))
                .toList();
    }

    public BossCard getBossByName(String bossName) {
        return getBossCards().stream()
                .filter(b -> b.getBossName().equals(bossName))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Card not found: " + bossName)
                );
    }
}
