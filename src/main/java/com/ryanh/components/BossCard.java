package com.ryanh.components;

import com.ryanh.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

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
    private final By noteTile = By.cssSelector("div.grid div.box-border:not(.animate-pulse)");
    //Edit note name button for created notes
    private final By editNoteNameButton = By.cssSelector("button[title='Edit note name']");
    //Text field that replaces the noteLink element when the editNoteNameButton is clicked
    private final By editNoteTextFieldInput = By.cssSelector("div.grid div.flex input");

    //Copy note button for created notes
    private final By copyNoteButton = By.cssSelector("div.grid div.flex button[title*='Copy this note']");
    //Delete note button for created notes
    private final By deleteNoteButton = By.cssSelector("div.grid div.flex button[title*='Delete note']");

    private final By deleteAlertWindow = By.cssSelector("div[role='alertdialog']");

    private final By deleteAlertButton = By.cssSelector("div[role='alertdialog'] button.bg-destructive");
    //Link for a note in the note table on a boss card
    private final By noteLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/raid/\"]");

    //A notification that appears after performing actions on a noteTile
    private final By toastNotification = By.cssSelector("section ol li");


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
     * Adds a note to a BossCard by clicking the add button, when a note is added the page automatically navigates to
     * that notes editing page. We check for the wait for the locator reference to become stale since we navigate to a
     * different page.
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
     * Checks that the note table contains a note for a BossCard
     * @return - True if a note exists in the table, false if there are none.
     */
    public boolean isTilePresent() {
        waitUntilVisible(noteTile);
        return !root.findElements(noteTile).isEmpty();
    }

    /**
     * Edit a note by clicking the edit button, typing into the input field, then clicking the edit button again to save
     * @param text - text to replace the current note text with
     * TODO - Make this faster, have to wait for toastNotification to disappear
     */
    public void editNote(String text) {
        //Due to how the tiles work, the element isn't initially present in the DoM until the noteTiles load.
        wait.until(ExpectedConditions.presenceOfElementLocated(editNoteNameButton));
        root.findElement(editNoteNameButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(editNoteTextFieldInput));
        WebElement textField = root.findElement(editNoteTextFieldInput);

        //Type the text into the input field, clicking the button again saves the note.
        textField.sendKeys(Keys.CONTROL + "a");
        textField.sendKeys(Keys.DELETE);
        textField.sendKeys(text);
        root.findElement(editNoteNameButton).click();

        //When an edit occurs, a toast notification pops up, can use these to see if the edit was successful.
        wait.until(ExpectedConditions.presenceOfElementLocated(toastNotification));
        //Waiting for the notification to disappear gives enough time for the edit to process
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
     * Gets the name of the note from the link
     * @return -
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
