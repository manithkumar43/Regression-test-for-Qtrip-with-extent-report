package qtriptest.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

public class HomePage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement LogOutButton;

    @FindBy(linkText = "Register")
    WebElement RegisterButton;

    @FindBy(id = "autocomplete")
    WebElement searchBox;

    @FindBy(xpath = "//ul[@id='results']/h5")
    WebElement invalidsearchSuggestion;

    // ----------------------------------------------------

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory =
                new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ----------------------------------------------------

    public void navigateToHomePage() {
        System.out.println("navigating to Home page");
        driver.get(url);

        // ✅ Synchronization: homepage loaded
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(searchBox));
    }

    // ----------------------------------------------------

    public void clickOnRegister() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement registerBtn =
                wait.until(ExpectedConditions.elementToBeClickable(RegisterButton));
                System.out.println("Searching and clicking the register button on home page");
        registerBtn.click();

        // ✅ Assertion: Register page opened
        wait.until(ExpectedConditions.urlContains("/register/"));
        Assert.assertTrue(
                driver.getCurrentUrl().endsWith("/register/"),
                "Register page not opened"
        );
    }

    // ----------------------------------------------------

    public void VerifyRegisterPageDisplayed() {
        System.out.println("Verifying register page  is opened");
        Assert.assertTrue(
                driver.getCurrentUrl().endsWith("/register/"),
                "User is NOT on Register Page"
        );
    }

    // ----------------------------------------------------

    public void performLogout() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(LogOutButton));
        LogOutButton.click();

        // ✅ Assertion: Logout successful
        wait.until(ExpectedConditions.visibilityOf(RegisterButton));
        Assert.assertTrue(
                RegisterButton.isDisplayed(),
                "Logout failed"
        );
    }

    // ----------------------------------------------------

    public void searchAndVerifyCity(String city, boolean shouldExist) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(city);

        if (shouldExist) {

            By cityResult = By.xpath(
                    "//ul[@id='results']//li[normalize-space()='" + city + "']"
            );

            WebElement suggestion =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(cityResult));

            Assert.assertTrue(
                    suggestion.isDisplayed(),
                    "Expected city not shown in autocomplete"
            );

            suggestion.click();

            // ✅ Assertion: City page opened
            wait.until(ExpectedConditions.urlContains("/pages/adventures"));
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("/pages/adventures"),
                    "City navigation failed"
            );

        } else {

            WebElement noCityFound =
                    wait.until(ExpectedConditions.visibilityOf(invalidsearchSuggestion));

            Assert.assertEquals(
                    noCityFound.getText().trim(),
                    "No City found",
                    "Invalid search message not displayed"
            );
        }
    }
}
