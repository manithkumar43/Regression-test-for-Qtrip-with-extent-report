package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_02 {

        static RemoteWebDriver driver;


        @Test(enabled = true, dataProvider = "testData", dataProviderClass = DP.class, priority = 2, groups = {"Search and Filter flow"})
        public void TestCase02(String city, String categoryFilter, String durationFilter,
                        String expectedFilteredResults, String expectedUnFilteredResults)
                        throws InterruptedException, MalformedURLException {

                driver = DriverSingleton.getDriver();

                HomePage homePage = new HomePage(driver);

                // 1️⃣ Navigate to Home Page
                homePage.navigateToHomePage();
                Assert.assertTrue(driver.getCurrentUrl().contains("qtripdynamic"),
                                "Home page did not load");

                // 2️⃣ Invalid city search
                // homePage.searchAndVerifyCity("InvalidCity123", false);

                // 3️⃣ Valid city search + autocomplete validation
                homePage.searchAndVerifyCity(city, true);

                // 4️⃣ Verify navigation to Adventures page
                Assert.assertTrue(driver.getCurrentUrl().contains("adventures"),
                                "User not navigated to Adventures page");

                AdventurePage adventurePage = new AdventurePage(driver);

                int expectedFiltered = Integer.parseInt(expectedFilteredResults);
                int expectedUnFiltered = Integer.parseInt(expectedUnFilteredResults);

                // 5️⃣ Duration filter validation
                adventurePage.selectDuration(durationFilter);
                // adventurePage.verifyResultCount(expectedFiltered);

                // 6️⃣ Category filter validation
                adventurePage.selectCategory(categoryFilter);
                adventurePage.verifyResultCount(expectedFiltered);

                // 7️⃣ Clear filters
                adventurePage.clearDuration();
                adventurePage.clearCategory();

                // 8️⃣ Verify all records after clearing filters
                adventurePage.verifyResultCount(expectedUnFiltered);
        }

        @AfterMethod
        public void tearDown() {
                DriverSingleton.quitDriver();
        }

}
