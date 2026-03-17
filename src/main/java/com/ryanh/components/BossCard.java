package com.ryanh.components;

import com.ryanh.base.BasePage;
import org.openqa.selenium.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * UI component on the Overview page that allows creation of cooldown notes and assignments for a specific boss.
 * TODO Add functionality for some of the less essential actions on a card.
 */
public class BossCard extends BasePage {
    private final WebElement root;

    /**
     * Selectors for elements on a boss card that are always available regardless on if there are any notes created.
     */
    private final By addNoteButton = By.cssSelector("div.grid div.flex button:not([title]):not(.border)");
    private final By expandViewButton = By.cssSelector("div.grid div.flex button[title='Open full view']");
    private final By bossName = By.cssSelector("div.grid div.flex a[href*='/viserio-cooldowns/guides'] span");
    private final By bossGuideLink = By.cssSelector("div.grid div.flex a[href*='/viserio-cooldowns/guides']");

    //Only available when no notes are created
    private final By createANoteButton = By.xpath(".//button[contains(text(), 'Create a note')]");

    //Locator for individual note tiles on this card
    private final By noteTile = By.cssSelector("div.grid div.box-border:not(.animate-pulse)");

    /**
     * When a BossCard is created, we set the root element so we can differentiate between different BossCards on the
     * Overview Page.
     *
     * @param driver - WebDriver
     * @param root   - Root element on the Overview page
     */
    public BossCard(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    /**
     * Returns the name of a boss by checking the text of the boss guide link for that boss.
     *
     * @return - Text wrapped around the link href
     */
    public String getBossName() {
        return root.findElement(bossName).getText();
    }

    /**
     * Add a note by clicking the add button, when a note is added the page automatically navigates to that
     * notes editing page. Wait for the locator reference to become stale since we navigate to a different page.
     */
    public void addNote() {
        root.findElement(addNoteButton).click();
        waitForStaleElement(root.findElement(addNoteButton));
        driver.navigate().back();
        waitUntilVisible(addNoteButton);
    }

    /**
     * Similar functionality to addNote but has to be handled differently due to the button only appearing when
     * there are no notes.
     */
    public void createNote() {
        waitUntilExists(createANoteButton);
        root.findElement(createANoteButton).click();
        waitForStaleElement(root.findElement(createANoteButton));
        driver.navigate().back();
        waitUntilExists(bossName);
    }

    public void openBossGuide() {
        root.findElement(bossGuideLink).click();
        waitForPageURL(getGuideURL());
    }

    /**
     * Checks that the note table contains a note for a BossCard.
     *
     * @return - True if a note exists in the table, false if there are none.
     */
    public boolean isTilePresent() {
        waitUntilVisible(noteTile);
        return !root.findElements(noteTile).isEmpty();
    }

    /**
     * Get the number of note tiles on a card.
     *
     * @return - The number of tiles.
     */
    public int getNumberOfTiles() {
        return root.findElements(noteTile).size();
    }

    /**
     * Returns all NoteTile components on this card.
     *
     * @return - List of NoteTile instances scoped to each tile element.
     */
    public List<NoteTile> getNoteTiles() {
        waitUntilVisible(noteTile);
        return root.findElements(noteTile)
                .stream()
                .map(el -> new NoteTile(driver, el))
                .toList();
    }

    /**
     * Returns the first NoteTile on this card.
     *
     * @return - The first NoteTile.
     * @throws NoSuchElementException if no tiles exist.
     */
    public NoteTile getFirstNoteTile() {
        List<NoteTile> tiles = getNoteTiles();
        if (tiles.isEmpty()) {
            throw new NoSuchElementException("No note tiles found on this boss card.");
        }
        return tiles.getFirst();
    }

    /**
     * Clears all notes on a card by deleting each tile.
     * TODO - Make this faster, have to wait for toastNotification to disappear
     */
    public void clearNotes() {
        while (!root.findElements(noteTile).isEmpty()) {
            getFirstNoteTile().delete();
        }
    }

    public String getGuideURL() {
        return root.findElement(bossGuideLink).getAttribute("href");
    }
}
