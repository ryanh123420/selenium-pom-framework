package com.ryanh.components;

import com.ryanh.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BossCard extends BasePage {
    //Root element where the card is located
    private final WebElement root;

    /**
     * Selectors for elements on a boss card that are always available regardless on if there are any notes created.
     */
    //The add button that is always shown in the top right corner of a boss card
    private final By addNoteButton = By.cssSelector("div.grid div.flex button:not([title]):not(.border)");
    //Expand view button in top right corner next to the add button
    private final By expandViewButton = By.cssSelector("div.grid div.flex button[title=\"Open full view\"]");
    //The guide link for a boss in the top left corner of a boss card
    private final By bossGuideLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/guides\"] span");

    /**
     * Selectors for elements on a boss card that are only available when no notes are created.
     */
    //The add button that only appears when a boss has no notes created
    private final By createANoteButton = By.xpath(".//button[contains(text(), 'Create a note')]");

    /**
     * Selectors for elements on a boss card that are only available when at least one note is created.
     */
    //The note tile containing the link to the note page, and all the buttons for note actions
    private final By noteTile = By.cssSelector("div.grid div.box-border");
    //Edit note name button for created notes
    private final By editNoteNameButton = By.cssSelector("div.grid div.flex button[title=\"Edit note name\"]");
    //Text field that replaces the noteLink element when the editNoteNameButton is clicked
    private final By editNoteTextField = By.cssSelector("div.grid div.flex input");

    //Copy note button for created notes
    private final By copyNoteButton = By.cssSelector("div.grid div.flex button[title*=\"Copy this note\"]");
    //Delete note button for created notes
    private final By deleteNoteButton = By.cssSelector("div.grid div.flex button[title*=\"Delete your note\"]");
    //Link for a note in the note table on a boss card
    private final By noteLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/raid/\"]");


    public BossCard(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    public String getBossName() {
        return root.findElement(bossGuideLink).getText();
    }

    public WebElement getRootElement() {
        return root;
    }

    public void addNote() {
        root.findElement(addNoteButton).click();
        waitForStaleElement(root.findElement(addNoteButton));
    }

    public void createNote() {
        click(createANoteButton);
    }

    public void expandView() {
        click(expandViewButton);
    }

    public void openBossGuide() {
        click(bossGuideLink);
    }

    public boolean isTilePresent() {
        waitUntilVisible(noteTile);
        return !root.findElements(noteTile).isEmpty();
    }

    public void editNote(String text) {
        click(editNoteNameButton);
        driver.findElement(editNoteTextField).clear();
        type(editNoteTextField, text);
        driver.findElement(editNoteTextField).sendKeys(Keys.ENTER);
    }

    public void copyNote() {
        click(copyNoteButton);
    }

    public void deleteNote() {
        click(deleteNoteButton);
    }

    public void openNote() {
        click(noteLink);
    }
}
