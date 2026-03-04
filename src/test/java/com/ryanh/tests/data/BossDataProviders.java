package com.ryanh.tests.data;

import org.testng.annotations.DataProvider;

public class BossDataProviders {

    @DataProvider(name = "TWWSeason3")
    public static Object[][] twwSeason3() {
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

    @DataProvider(name = "MidnightSeason1")
    public static Object[][] midnightSeason1() {
        return new Object[][]{
                {"Imperator Averzian"},
                {"Vorasius"},
                {"Fallen-King Salhadaar"},
                {"Vaelgor & Ezzorak"},
                {"Lightblinded Vanguard"},
                //{"Crown of the Cosmos"},
                {"Chimaerus the Undreamt God"},
                {"Belo'ren, Child of Al'ar"},
                //{"Midnight Falls"},
        };
    }
}
