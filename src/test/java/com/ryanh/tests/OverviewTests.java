package com.ryanh.tests;
import com.google.common.base.Verify;
import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test scripts for the webapp raid overview page: hhttps://wowutils.com/viserio-cooldowns/raid/overview
 */
public class OverviewTests extends BaseTest{
    OverviewPage overviewPage;

    /**
     * Boss names for Manaforge Omega raid
     */
    @DataProvider(name = "mfoBossList")
    public Object[][] createData1() {
        return new Object[][] {
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

    /**
     * Adds one note to every boss card on the overview page
     * @param bossName
     */
    @Test(dataProvider = "mfoBossList")
    public void addNotes(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        boss.addNote();

        //Adding a note to a BossCard causes forced navigation to the page for that note, we need to navigate back
        driver.navigate().back();
        overviewPage.waitForPageLoad();

        //We need to refresh the boss list due to the navigation to avoid stale elements
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);
        Assert.assertTrue(refreshedBoss.isTilePresent());
    }

    /**
     * Edits the first note on each boss card, runs after addNotes
     * @param bossName
     */
    @Test(dataProvider = "mfoBossList", dependsOnMethods = "addNotes")
    public void editNotes(String bossName) {
        String editedName = "Edited - " + bossName;
        BossCard boss = overviewPage.getBossByName(bossName);
        boss.editNote(editedName);

        Assert.assertEquals(boss.getNoteName(), editedName);
    }

    @Test(dependsOnMethods = "editNotes")
    public void copyNotes() {
        List<BossCard> bossList = overviewPage.getBossCards();
        for(BossCard boss : bossList) {
            boss.copyNote();
        }
    }

    /**
     * Deletes one note from each BossCard
     */
    @Test(dependsOnMethods = "copyNotes")
    public void deleteNotes() {
        List<BossCard> bossList = overviewPage.getBossCards();
        for(BossCard boss : bossList) {
            boss.deleteNote();
        }
    }

    /**
     * Clears all notes on the Overview page
     */
    @Test
    public void clearAllNotes() {
        List<BossCard> bossList = overviewPage.getBossCards();
        for(BossCard boss : bossList) {
            boss.clearNotes();
        }
    }
}
