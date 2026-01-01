package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    RemoteWebDriver driver;

    // Locators
    @FindBy(xpath = "//input[@name='email']")
    WebElement email;

    @FindBy(xpath = "//input[@name='password']")
    WebElement password;

    @FindBy(className = "btn-login")
    WebElement loginButton;

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement LogOutButton;

    // Constructor
    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory =
                new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ----------------------------------------------------

    public void navigateToLoginPage() {
        driver.get(url);

        // ✅ Synchronization: login page loaded
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(email));
        System.out.println("User is on Login");
    }

    // ----------------------------------------------------

    public boolean performLogin(String username, String Password) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // ✅ Synchronization before typing
        wait.until(ExpectedConditions.visibilityOf(email));
        // email.clear();
        // email.sendKeys(username);
         //applying selenium Wrapper class
        SeleniumWrapper.advSendKeys(email, username);

        // password.clear();
        // password.sendKeys(Password);
        //applying selenium Wrapper class
        SeleniumWrapper.advSendKeys(password, Password);

        // ✅ Synchronization before click
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        // loginButton.click();
        SeleniumWrapper.advClick(loginButton, driver);
        System.out.println("User login complete  searching for Logoout button");

        // ✅ Synchronization after login
        try {
            WebElement logoutBtn = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Logout']"))
            );
            String currenUrl=driver.getCurrentUrl();
            System.out.println(currenUrl);
            return logoutBtn.isDisplayed();
        } catch (Exception e) {
            System.out.println("Logout button not found after login. Login might have failed.");
            String currenUrl=driver.getCurrentUrl();
            System.out.println(currenUrl);
            
            return false;
        }
        
    }
}
