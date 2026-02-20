package com.ryanh.components;

import com.ryanh.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

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
    private final By expandViewButton = By.cssSelector("div.grid div.flex button[title=\"Open full view\"]");
    private final By bossGuideLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/guides\"] span");

    //Only available when no notes are created
    private final By createANoteButton = By.xpath(".//button[contains(text(), 'Create a note')]");

    /**
     * Selectors for elements on a boss card that are only available when at least one note is created.
     */
    private final By noteTile = By.cssSelector("div.grid div.box-border:not(.animate-pulse)");
    private final By editNoteNameButton = By.cssSelector("button[title='Edit note name']");
    private final By editNoteTextFieldInput = By.cssSelector("div.grid div.flex input");
    private final By copyNoteButton = By.cssSelector("div.grid div.flex button[title*='Copy this note']");
    private final By deleteNoteButton = By.cssSelector("div.grid div.flex button[title*='Delete note']");
    private final By deleteAlertWindow = By.cssSelector("div[role='alertdialog']");
    private final By deleteAlertButton = By.cssSelector("div[role='alertdialog'] button.bg-destructive");
    private final By noteLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/raid/\"]");

    //Notification that appears in top right corner after performing certain actions
    private final By toastNotification = By.cssSelector("section ol li");

    /**
     * When a BossCard is created, we set the root element so we can differentiate between different BossCards on the
     * Overview Page.
     * @param driver - WebDriver
     * @param root - Root element on the Overview page
     */
    public BossCard(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    /**
     * Returns the name of a boss by checking the text of the boss guide link for that boss.
     * @return - Text wrapped around the link href
     */
    public String getBossName() {
        return root.findElement(bossGuideLink).getText();
    }

    public WebElement getRootElement() {
        return root;
    }

    /**
     * Add a note by clicking the add button, when a note is added the page automatically navigates to that
     * notes editing page. Wait for the locator reference to become stale since we navigate to a different page.
     */
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

    /**
     * Checks that the note table contains a note for a BossCard.
     * @return - True if a note exists in the table, false if there are none.
     */
    public boolean isTilePresent() {
        waitUntilVisible(noteTile);
        return !root.findElements(noteTile).isEmpty();
    }

    /**
     * Get the number of note tiles on a card.
     * @return - The number of tiles.
     */
    public int getNumberOfTiles() {
        return root.findElements(noteTile).size();
    }

    /**
     * Edit a note by clicking the edit button, typing into the input field, then clicking the edit button again to save
     * @param text - text to replace the current note text with
     * TODO - Make this faster, have to wait for toastNotification to disappear
     */
    public void editNote(String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(editNoteNameButton));
        root.findElement(editNoteNameButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(editNoteTextFieldInput));
        WebElement textField = root.findElement(editNoteTextFieldInput);

        textField.sendKeys(Keys.CONTROL + "a");
        textField.sendKeys(Keys.DELETE);
        textField.sendKeys(text);
        //Clicking again saves the note.
        root.findElement(editNoteNameButton).click();

        //When an edit occurs, a toast notification pops up, can use these to see if the edit was successful.
        wait.until(ExpectedConditions.presenceOfElementLocated(toastNotification));
        //Waiting for the notification to disappear gives enough time for the edit to process.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
    }

    /**
     * Copies the first note on a card.
     */
    public void copyNote() {
        wait.until(ExpectedConditions.presenceOfElementLocated(copyNoteButton));
        wait.until(ExpectedConditions.elementToBeClickable(copyNoteButton));
        root.findElement(copyNoteButton).click();
    }

    /**
     * Deletes one note on a card
     */
    public void deleteNote() {
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteNoteButton));
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        root.findElement(deleteNoteButton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(deleteAlertWindow));
        driver.findElement(deleteAlertButton).click();
    }

    /**
     * Gets the name of the note from its link.
     * @return - Name of the note.
     */
    public String getNoteName() {
        waitUntilExists(noteLink);
        return root.findElement(noteLink).getText();
    }

    public void openNote() {
        click(noteLink);
    }

    /**
     * Clears all notes on a card
     * TODO - Make this faster, have to wait for toastNotification to disappear
     */
    public void clearNotes() {
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteNoteButton));
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        List<WebElement> deleteButtons =  root.findElements(deleteNoteButton);

        for(WebElement button : deleteButtons) {
            button.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(deleteAlertWindow));
            driver.findElement(deleteAlertButton).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(toastNotification));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
        }
    }
}
