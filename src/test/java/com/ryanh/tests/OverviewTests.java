package com.ryanh.tests;

import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.components.NoteTile;
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
    private OverviewPage overviewPage;

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

        //Refresh the boss list due to the navigation to avoid stale elements
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);
        Assert.assertTrue(refreshedBoss.isTilePresent());

        refreshedBoss.getFirstNoteTile().delete();
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
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);

        NoteTile tile = refreshedBoss.getFirstNoteTile();
        tile.edit(editedName);

        Assert.assertEquals(tile.getName(), editedName);

        tile.delete();
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
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);

        refreshedBoss.getFirstNoteTile().copy();
        Assert.assertTrue(refreshedBoss.getNumberOfTiles() > tileAmount);

        refreshedBoss.clearNotes();
    }

    @Test(dataProvider = "mfoBossList")
    public void guideLinkNavigation(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        String guideURL = boss.getGuideURL();
        boss.openBossGuide();
        Assert.assertEquals(guideURL, driver.getCurrentUrl());
    }

    @Test
    public void dropdownTest() {
        overviewPage.clickDropdown("Last Updated");
        Assert.assertEquals(overviewPage.getSelectedSortOption(), "Last Updated");
        overviewPage.clickDropdown("Creation Date");
        Assert.assertEquals(overviewPage.getSelectedSortOption(), "Creation Date");
    }
}

