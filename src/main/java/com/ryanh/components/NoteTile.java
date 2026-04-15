package com.ryanh.components;

import com.ryanh.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * UI component representing an individual note tile on a BossCard.
 * Scoped to a single tile's root WebElement.
 */
public class NoteTile extends BasePage {
    private final WebElement root;
    private final By copyNoteButton = By.cssSelector("div.grid div.flex button[title*='Copy this note']");
    private final By deleteNoteButton = By.cssSelector("div.grid div.flex button[title*='Delete note']");
    private final By deleteAlertWindow = By.cssSelector("div[role='alertdialog']");
    private final By deleteAlertButton = By.cssSelector("div[role='alertdialog'] button.bg-destructive");
    private final By noteLink = By.cssSelector("div.grid div.flex a[href*=\"/viserio-cooldowns/raid/\"]");

    private final By toastNotification = By.cssSelector("section ol li");

    public NoteTile(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    /**
     * Copies this note.
     */
    public void copy() {
        wait.until(ExpectedConditions.presenceOfElementLocated(copyNoteButton));
        wait.until(ExpectedConditions.elementToBeClickable(copyNoteButton));
        root.findElement(copyNoteButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(toastNotification));
    }

    /**
     * Deletes this note. Confirms in the alert dialog.
     */
    public void delete() {
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteNoteButton));
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        root.findElement(deleteNoteButton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(deleteAlertWindow));
        driver.findElement(deleteAlertButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(toastNotification));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(toastNotification));
    }

    /**
     * Gets the name of this note from its link text.
     * @return - Name of the note.
     */
    public String getName() {
        waitUntilExists(noteLink);
        return root.findElement(noteLink).getText();
    }

    /**
     * Opens this note by clicking its link.
     */
    public void open() {
        click(noteLink);
    }
}
