package com.ryanh.tests;
import com.google.common.base.Verify;
import com.ryanh.base.BaseTest;
import com.ryanh.components.BossCard;
import com.ryanh.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class OverviewTests extends BaseTest{

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

    @Test(dataProvider = "mfoBossList")
    public void addNotes(String bossName) {
        OverviewPage overviewPage = new OverviewPage(driver);
        driver.get("https://wowutils.com/viserio-cooldowns/raid/overview");
        overviewPage.waitForPageLoad();

        BossCard boss = overviewPage.getBossByName(bossName);
        boss.addNote();

        driver.navigate().back();
        overviewPage.waitForPageLoad();

        BossCard refreshedBoss = overviewPage.getBossByName(bossName);
        Assert.assertTrue(refreshedBoss.isTilePresent());
    }
}
