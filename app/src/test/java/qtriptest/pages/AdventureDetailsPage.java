package qtriptest.pages;

import org.openqa.selenium.By;
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

        // ✅ Wait for booking form to be ready
        wait.until(ExpectedConditions.presenceOfElementLocated(guestNameInput));

        System.out.println("Entering guest name");
        driver.findElement(guestNameInput).clear();
        driver.findElement(guestNameInput).sendKeys(name);

        System.out.println("Entering date");
        driver.findElement(dateInput).clear();
        driver.findElement(dateInput).sendKeys(date);

        System.out.println("Entering count");
        driver.findElement(personInput).clear();
        driver.findElement(personInput).sendKeys(String.valueOf(count));

        System.out.println("Clicking on reservation");
        driver.findElement(reserveButton).click();
        System.out.println("Reservation submitted");
    }

    // ----------------------------------------------------

    public void bookingSuccess() {

        // ✅ Stale-safe success check
        wait.until(ExpectedConditions.presenceOfElementLocated(successBanner));

        String bannerText = driver.findElement(successBanner).getText().toLowerCase();

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

        wait.until(ExpectedConditions.urlContains("reservations"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("reservations"),
                "Failed to navigate to History page"
        );

        System.out.println("navigated to History page");
    }
}
