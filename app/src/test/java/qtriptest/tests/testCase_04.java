package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testCase_04 extends BaseTest {


    // RemoteWebDriver driver;

    // @BeforeMethod
    // public void beforeclass() throws MalformedURLException {
    //     driver = DriverSingleton.getDriver();
    // }
    

    public String[] parseDataset(String dataset) {
        return dataset.split(";");
    }
    


    @Test(enabled = true, dataProvider="testData", dataProviderClass = DP.class, priority = 4, groups = {"Reliability Flow"})
    public void TestCase04(String Username, String Password, String booking1, String booking2, String booking3){
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

        String[] bookings = { booking1, booking2, booking3 };

        for(String b : bookings) {
            String[] parts = parseDataset(b);
        
            String city = parts[0];       // "Beach"
            String adventure = parts[1];           // "Goa"
            String guestName = parts[2];  // "Parasailing"
            String date = parts[3];           // "2026-01-15"
            Integer count = Integer.valueOf(parts[4]);         // "2"
            
            // Now you can use these values in your page object methods

            homePage.searchAndVerifyCity(city, loginStatus);
            AdventurePage adv = new AdventurePage(driver);
            adv.clickonAdventure(adventure);

            // ===== Book Adventure =====
            AdventureDetailsPage advPage = new AdventureDetailsPage(driver);
            advPage.adventureBooking(guestName, date, count);
            advPage.bookingSuccess();
            homePage.navigateToHomePage();
            

        }
        AdventureDetailsPage advPage = new AdventureDetailsPage(driver);
        advPage.navigateToHistory();

        // ===== History Page =====
        HistoryPage historyPage = new HistoryPage(driver);
        historyPage.validateHistoryPage();

        List<String> beforeIds = historyPage.getAllTransactionIds();
        System.out.println(beforeIds);  
       
    }

    // @AfterMethod
    // public void tearDown(){
    //     DriverSingleton.quitDriver();
    // }


}
