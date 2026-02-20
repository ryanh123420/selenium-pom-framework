package com.ryanh.tests;

import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;

/**
 * Test scripts for the webapp raid overview page: https://wowutils.com/viserio-cooldowns/raid/overview
 * TODO: Add tests for non-BossCard related actions on the Ovewview page.
 */
public class OverviewTests extends BaseTest {
    OverviewPage overviewPage;

    /**
     * Boss names for Manaforge Omega (War Within Season 3)
     * TODO: Create a DataProvider class if I decide to add more seasons/raids.
     *  Could also be used on Setups page later.
     */
    @DataProvider(name = "mfoBossList")
    public Object[][] createData1() {
        return new Object[][]{
                {"Plexus Sentinel"},
                {"Loom'ithar"},
                {"Soulbinder Naazindhri"},
                {"Forgeweaver Araz"},
                {"The Soul Hunters"},
                {"Fractillus"},
                {"Nexus‑King Salhadaar"},
                {"Dimensius, the All‑Devouring"},
        };
    }

    @BeforeMethod
    public void overViewSetup() {
        overviewPage = new OverviewPage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns/raid/overview");
        overviewPage.waitForPageLoad();
    }

    @AfterMethod
    public void cleanNotes() {
        //Delete all notes after each test?
    }

    /**
     * Adds one note to every boss card on the overview page.
     * @param bossName - Name of a specific boss
     */
    @Test(dataProvider = "mfoBossList")
    public void addNotes(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        boss.addNote();

        //Adding a note to a BossCard causes forced navigation to the page for that note, we need to navigate back
        driver.navigate().back();
        overviewPage.waitForPageLoad();

        //Refresh the boss list due to the navigation to avoid stale elements
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);
        Assert.assertTrue(refreshedBoss.isTilePresent());

        refreshedBoss.deleteNote();
    }

    /**
     * Edits the first note on each boss card.
     * @param bossName - Name of a specific boss
     */
    @Test(dataProvider = "mfoBossList")
    public void editNotes(String bossName) {
        String editedName = "Edited - " + bossName;
        BossCard boss = overviewPage.getBossByName(bossName);

        boss.addNote();
        driver.navigate().back();
        overviewPage.waitForPageLoad();
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);

        refreshedBoss.editNote(editedName);

        Assert.assertEquals(refreshedBoss.getNoteName(), editedName);

        refreshedBoss.deleteNote();
    }

    /**
     * Copy the first note on each boss card.
     * @param bossName
     */
    @Test(dataProvider = "mfoBossList")
    public void copyNotes(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        int tileAmount = boss.getNumberOfTiles();

        boss.addNote();
        driver.navigate().back();
        overviewPage.waitForPageLoad();
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);

        refreshedBoss.copyNote();
        Assert.assertTrue(refreshedBoss.getNumberOfTiles() > tileAmount);

        refreshedBoss.clearNotes();
    }

    /**
     * Clears all notes on the Overview page.
     */
    @Test(enabled = false)
    public void clearAllNotes() {
        List<BossCard> bossList = overviewPage.getBossCards();
        for (BossCard boss : bossList) {
            boss.clearNotes();
        }
    }
}

