package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_01 {

    static RemoteWebDriver driver;
    public static String lastGeneratedUserName;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();
    }

    @Test(enabled = true, dataProvider = "testData", dataProviderClass = DP.class)
    public void TestCase01(String Username, String Password) {

        // ---------- REGISTER ----------
        RegisterPage registrationPage = new RegisterPage(driver);
        registrationPage.navigateToRegisterPage();

        boolean registrationStatus =
                registrationPage.performRegistration("TestUser", "TestPass@123", true);

        Assert.assertTrue(
                registrationStatus,
                "❌ Registration failed: User was not redirected to Login page"
        );

        lastGeneratedUserName = registrationPage.lastGeneratedUsername;

        //To Use the dynamicly generated user just replace the username with lastGeneratedUserName in perform login method parameter.

        // ---------- LOGIN ----------
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();

        boolean loginStatus =
                loginPage.performLogin(Username, Password);

        Assert.assertTrue(
                loginStatus,
                "❌ Login failed: User was not redirected to Home page"
        );

        // ---------- LOGOUT ----------
        HomePage homePage = new HomePage(driver);
        homePage.performLogout();

        // Optional assertion after logout
        Assert.assertTrue(
                driver.getCurrentUrl().endsWith("/"),
                "❌ Logout failed: User did not return to Home page"
        );
    }
}
