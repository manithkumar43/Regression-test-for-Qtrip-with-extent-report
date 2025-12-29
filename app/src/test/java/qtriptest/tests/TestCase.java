package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase {

    static RemoteWebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        // Launch Browser using Zalenium
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("createDriver()");
    }

    @Test(enabled =false)
    public void testLogin(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.performLogin("test123@gmail.com", "test123");

    }

    @Test(enabled = false)
    public void TestCase01() {
    
        RegisterPage registrationPage = new RegisterPage(driver);
        registrationPage.navigateToRegisterPage();
    
        boolean registrationStatus =
                registrationPage.performRegistration("TestUser", "TestPass@123", true);
    
        if (!registrationStatus) {
            System.out.println("Registration failed. Test stopped.");
            return;
        }
    
        String lastGeneratedUserName = registrationPage.lastGeneratedUsername;
    
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
    
        boolean loginStatus =
                loginPage.performLogin(lastGeneratedUserName, "TestPass@123");
    
        if (!loginStatus) {
            System.out.println("Login failed. Test stopped.");
            return;
        }
    
        HomePage homePage = new HomePage(driver);
        homePage.performLogout();
    }
    
    
}
