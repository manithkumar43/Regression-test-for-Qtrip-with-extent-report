package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdventureDetailsPage {

    RemoteWebDriver driver;
    WebDriverWait wait;

    // ✅ Use By locators for dynamic waits
    private By guestNameInput = By.xpath("//input[@name='name']");
    private By dateInput = By.xpath("//input[@name='date']");
    private By personInput = By.xpath("//input[@name='person']");
    private By reserveButton = By.xpath("//button[@type='submit']");
    private By successBanner = By.id("reserved-banner");
    private By historyLink = By.linkText("Reservations");

    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        AjaxElementLocatorFactory factory =
                new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ----------------------------------------------------

    public void adventureBooking(String name, String date, int count) {

        // ✅ Booking form should be present
        Assert.assertTrue(
                wait.until(ExpectedConditions.presenceOfElementLocated(guestNameInput)) != null,
                "Guest name input is not present"
        );
    
        // 🔹 Guest name
        WebElement guestName =
                SeleniumWrapper.findElementWithRetry(driver, guestNameInput, 3);
        Assert.assertNotNull(guestName, "Guest name element not found");
    
        Assert.assertTrue(
                SeleniumWrapper.advSendKeys(guestName, name),
                "Failed to enter guest name"
        );
    
        // 🔹 Date
        WebElement dateInputElement =
                SeleniumWrapper.findElementWithRetry(driver, dateInput, 3);
        Assert.assertNotNull(dateInputElement, "Date input element not found");
    
        Assert.assertTrue(
                SeleniumWrapper.advSendKeys(dateInputElement, date),
                "Failed to enter date"
        );
    
        // 🔹 Person count 
        WebElement personInputElement =
                SeleniumWrapper.findElementWithRetry(driver, personInput, 3);
        Assert.assertNotNull(personInputElement, "Person count element not found");
    
        Assert.assertTrue(
                SeleniumWrapper.advSendKeys(personInputElement, String.valueOf(count)),
                "Failed to enter person count"
        );
    
        // 🔹 Reserve button
        WebElement reserveButtonElement =
                SeleniumWrapper.findElementWithRetry(driver, reserveButton, 3);
        Assert.assertNotNull(reserveButtonElement, "Reserve button not found");
    
        Assert.assertTrue(
                SeleniumWrapper.advClick(reserveButtonElement, driver),
                "Failed to click Reserve button"
        );
    
        // 🔹 Success banner assertion
        WebElement successMessage =
                wait.until(ExpectedConditions.visibilityOfElementLocated(successBanner));
    
        Assert.assertTrue(
                successMessage.isDisplayed(),
                "Success banner is not displayed"
        );
    
        Assert.assertTrue(
                successMessage.getText().toLowerCase().contains("success"),
                "Booking failed. Message: " + successMessage.getText()
        );
    }

    // ----------------------------------------------------

    public void bookingSuccess() {

        // ✅ Stale-safe success check
        wait.until(ExpectedConditions.presenceOfElementLocated(successBanner));

        //String bannerText = driver.findElement(successBanner).getText().toLowerCase();
        WebElement bannerTextElement= SeleniumWrapper.findElementWithRetry(driver, successBanner, 3);
        String bannerText = bannerTextElement.getText().toLowerCase();


        Assert.assertTrue(
                bannerText.contains("success"),
                "Booking success message not displayed"
        );

        System.out.println("✅ Booking is successful");
    }

    // ----------------------------------------------------

    public void navigateToHistory() {

        wait.until(ExpectedConditions.elementToBeClickable(historyLink));
        driver.findElement(historyLink).click();

        WebElement historyLinkElement= SeleniumWrapper.findElementWithRetry(driver, historyLink, 3);
        SeleniumWrapper.advClick(historyLinkElement, driver);


        wait.until(ExpectedConditions.urlContains("reservations"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("reservations"),
                "Failed to navigate to History page"
        );

        System.out.println("navigated to History page");
    }
}
