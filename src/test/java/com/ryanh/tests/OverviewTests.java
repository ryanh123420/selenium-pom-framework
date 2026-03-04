package com.ryanh.tests;

import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.components.NoteTile;
import com.ryanh.pages.OverviewPage;
import com.ryanh.tests.data.BossDataProviders;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test scripts for the webapp raid overview page: https://wowutils.com/viserio-cooldowns/raid/overview
 * TODO: Add tests for non-BossCard related actions on the Overview page.
 */
public class OverviewTests extends BaseTest {
    private OverviewPage overviewPage;

    @BeforeMethod
    public void overViewSetup() {
        overviewPage = new OverviewPage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns/raid/overview");
        overviewPage.showPersonalNotes();
        Assert.assertEquals(overviewPage.getSelectedGroupOption(), "Personal");
    }

    @AfterMethod
    public void cleanNotes() {
        //Delete all notes after each test?
    }

    /**
     * Adds one note to every boss card on the overview page.
     * @param bossName - Name of a specific boss
     */
    @Test(dataProvider = "MidnightSeason1", dataProviderClass = BossDataProviders.class)
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
    @Test(dataProvider = "MidnightSeason1", dataProviderClass = BossDataProviders.class)
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
     * @param bossName - Name of a specific boss
     */
    @Test(dataProvider = "MidnightSeason1", dataProviderClass = BossDataProviders.class)
    public void copyNotes(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        int tileAmount = boss.getNumberOfTiles();

        boss.addNote();
        BossCard refreshedBoss = overviewPage.getBossByName(bossName);

        refreshedBoss.getFirstNoteTile().copy();
        Assert.assertTrue(refreshedBoss.getNumberOfTiles() > tileAmount);

        refreshedBoss.clearNotes();
    }

    @Test(dataProvider = "MidnightSeason1", dataProviderClass = BossDataProviders.class)
    public void guideLinkNavigation(String bossName) {
        BossCard boss = overviewPage.getBossByName(bossName);
        String guideURL = boss.getGuideURL();
        boss.openBossGuide();
        Assert.assertEquals(guideURL, driver.getCurrentUrl());
    }

    @Test
    public void dropdownTest() {
        overviewPage.clickSortDropdown("Last Updated");
        Assert.assertEquals(overviewPage.getSelectedSortOption(), "Last Updated");
        overviewPage.clickSortDropdown("Creation Date");
        Assert.assertEquals(overviewPage.getSelectedSortOption(), "Creation Date");
    }
}

