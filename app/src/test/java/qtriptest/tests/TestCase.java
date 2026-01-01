package qtriptest.tests;

import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase extends BaseTest {
    // static ExtentReports reports;
    // static ExtentTest test;

    // static RemoteWebDriver driver;

    // @BeforeMethod
    // public void beforeclass() throws MalformedURLException {
    //         driver = DriverSingleton.getDriver();
    //         reports=ReportSingleton.getReport();
    //         test=reports.startTest("Testcase01");
    // }
    

    @Test(enabled =false)
    public void testLogin(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.performLogin("test123@gmail.com", "test123");
        System.out.println(System.getProperty("user.dir"));

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
    
    // @AfterMethod
    // public void tearDown() {
            
    //     reports.endTest(test);
    //     reports.flush();
    //     DriverSingleton.quitDriver();
    // }
    
}
