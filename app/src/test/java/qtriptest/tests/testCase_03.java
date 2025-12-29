package qtriptest.tests;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

public class testCase_03 {

    RemoteWebDriver driver;

    @BeforeMethod
    public void beforeclass() throws MalformedURLException {
        driver = DriverSingleton.getDriver();
    }

    @Test(enabled = true, dataProvider = "testData", dataProviderClass = DP.class, priority = 3, groups = {"Booking and Cancellation Flow"})
    public void TestCase03(
            String Username,
            String Password,
            String city,
            String adventure,
            String guestName,
            String date,
            String count
    ) throws MalformedURLException, InterruptedException {

        int guestCount = Integer.parseInt(count);

        // ===== Home Page =====
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        // ===== Register =====
        System.out.println("Starting Register form here -->");
        homePage.clickOnRegister();
        homePage.VerifyRegisterPageDisplayed();

        RegisterPage register = new RegisterPage(driver);
        System.out.println("Performing dynamic registration");

        // 🔑 Always create dynamic user
        register.performRegistration(Username, Password, true);

        // 🔑 Always login with generated user
        String loginUsername = register.lastGeneratedUsername;
        System.out.println("Logging in with new user: " + loginUsername);

        // ===== Navigate Home =====
        homePage.navigateToHomePage();

        // ===== Login =====
        LoginPage login = new LoginPage(driver);
        login.navigateToLoginPage();

        boolean loginStatus = login.performLogin(loginUsername, Password);
        if (!loginStatus) {
            throw new AssertionError("Login failed for user: " + loginUsername);
        }

        // ===== Search City =====
        homePage.searchAndVerifyCity(city, true);

        // ===== Select Adventure =====
        AdventurePage adv = new AdventurePage(driver);
        adv.clickonAdventure(adventure);

        // ===== Book Adventure =====
        AdventureDetailsPage advPage = new AdventureDetailsPage(driver);
        advPage.adventureBooking(guestName, date, guestCount);
        advPage.bookingSuccess();

        // ===== Navigate to History =====
        advPage.navigateToHistory();

        // ===== History Page =====
        HistoryPage historyPage = new HistoryPage(driver);
        historyPage.validateHistoryPage();

        List<String> beforeIds = historyPage.getAllTransactionIds();
        System.out.println(beforeIds);

        historyPage.cancelFirstReservation();
        historyPage.refreshPage();
        historyPage.verifyTransactionRemoved(beforeIds);
    }

    @AfterMethod
    public void tearDown() {
        DriverSingleton.quitDriver();
    }
}
