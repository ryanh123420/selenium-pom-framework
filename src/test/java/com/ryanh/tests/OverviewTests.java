package com.ryanh.tests;
import com.google.common.base.Verify;
import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test scripts for the webapp raid overview page: hhttps://wowutils.com/viserio-cooldowns/raid/overview
 */
public class OverviewTests extends BaseTest{

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

    /**
     * Adds one note to every boss card on the overview page
     * @param bossName
     */
    @Test(dataProvider = "mfoBossList")
    public void addNotes(String bossName) {
        OverviewPage overviewPage = new OverviewPage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns/raid/overview");
        overviewPage.waitForPageLoad();
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
        OverviewPage overviewPage = new OverviewPage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns/raid/overview");
        overviewPage.waitForPageLoad();

        String editedName = "Edited - " + bossName;
        BossCard boss = overviewPage.getBossByName(bossName);
        boss.editNote(editedName);

        Assert.assertEquals(boss.getNoteName(), editedName);
    }
}
