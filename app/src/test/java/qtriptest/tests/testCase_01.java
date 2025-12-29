package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_01 {

        static RemoteWebDriver driver;
        public static String lastGeneratedUserName;

        @BeforeMethod
        public void beforeclass() throws MalformedURLException {
                driver = DriverSingleton.getDriver();
        }


        @Test(enabled = true, dataProvider = "testData", dataProviderClass = DP.class, priority = 1, groups = 
        {"Login Flow"})
        public void TestCase01(String Username, String Password) throws MalformedURLException {

                driver = DriverSingleton.getDriver();

                // ---------- REGISTER ----------
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

                // ---------- LOGOUT ----------

                homePage.performLogout();

                // Optional assertion after logout
                Assert.assertTrue(driver.getCurrentUrl().endsWith("/"),
                                " Logout failed: User did not return to Home page");
        }

        @AfterMethod
        public void tearDown() {
                DriverSingleton.quitDriver();
        }
}
