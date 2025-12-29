package qtriptest.pages;

import java.sql.Timestamp;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    RemoteWebDriver driver;
    public String lastGeneratedUsername = "";

    @FindBy(id = "floatingInput")
    WebElement email;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(name = "confirmpassword")
    WebElement confirmPassword;

    @FindBy(xpath = "//button[text()='Register Now']")
    WebElement RegisterButon;

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory =
                new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ----------------------------------------------------

    public void navigateToRegisterPage() {
        driver.get(url);

        // ✅ Synchronization: page loaded
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(email));
    }

    // ----------------------------------------------------
//Extracting the user form TC_1_@gmail.com
    private String extractUsername(String email) {

        if (email == null) {
            return "";
        }
    
        // If email contains '@', take only part before it
        if (email.contains("@")) {
            return email.substring(0, email.indexOf("@"));
        }
    
        // Already a plain username
        return email;
    }
    
    //Perform dynamic user generation------

    public boolean performRegistration(
        String Username,
        String Password,
        Boolean makeUserDynamic) {

    System.out.println("Performing registration flow from here  -->");

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String test_data_username;

    // ✅ FIX: sanitize username first
    String cleanUsername = extractUsername(Username);

    if (makeUserDynamic) {
        test_data_username =
                cleanUsername + "_" + timestamp.getTime() + "@gmail.com";
    } else {
        test_data_username = cleanUsername + "@gmail.com";
    }

    WebDriverWait wait = new WebDriverWait(driver, 10);

    wait.until(ExpectedConditions.elementToBeClickable(email));
    email.clear();
    email.sendKeys(test_data_username);

    password.clear();
    password.sendKeys(Password);

    confirmPassword.clear();
    confirmPassword.sendKeys(Password);

    wait.until(ExpectedConditions.elementToBeClickable(RegisterButon));
    RegisterButon.click();

    wait.until(ExpectedConditions.urlContains("/login"));

    this.lastGeneratedUsername = test_data_username;
    System.out.println("Registration complete with user: " + test_data_username);

    return driver.getCurrentUrl().endsWith("/login/");
}


    // ----------------------------------------------------

    public boolean registration(String Username, String Password) {

        String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";
        WebDriverWait wait = new WebDriverWait(driver, 10);
    
        // Synchronization for email input
        wait.until(ExpectedConditions.visibilityOf(email)).clear();
        email.sendKeys(Username);
    
        password.clear();
        password.sendKeys(Password);
    
        confirmPassword.clear();
        confirmPassword.sendKeys(Password);
    
        wait.until(ExpectedConditions.elementToBeClickable(RegisterButon)).click();
    
        try {
            // Wait for alert if it exists
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            System.out.println("Alert accepted: Email already exists");
    
            // Navigate to login page and perform login
            driver.get(loginUrl);
    
            // Use the same username and password to login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.performLogin(Username, Password);
    
            return true; // logged in successfully after alert
        } catch (Exception e) {
            // No alert – registration succeeded
            System.out.println("No alert, registration successful");
    
            // Wait until URL contains login page after registration
            wait.until(ExpectedConditions.urlContains("/login"));
            return driver.getCurrentUrl().endsWith("/login");
        }
    }
    
}
