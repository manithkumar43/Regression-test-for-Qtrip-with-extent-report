package qtriptest.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

public class AdventurePage {

    RemoteWebDriver driver;

    @FindBy(id = "duration-select")
    WebElement durationDropdown;

    @FindBy(id = "category-select")
    WebElement categoryDropdown;

    @FindBy(xpath = "//div[@class='activity-card']")
    List<WebElement> activityCards;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    WebElement clearDurationBtn;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    WebElement clearCategoryBtn;

    // ----------------------------------------------------

    public AdventurePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory =
                new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ================= DURATION FILTER =================

    public void selectDuration(String durationText) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(durationDropdown));

        Select select = new Select(durationDropdown);
        select.selectByVisibleText(durationText.trim());

        // ✅ Wait for cards to refresh
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//div[@class='activity-card']"), 0));

        Assert.assertTrue(
                activityCards.size() > 0,
                "No activities shown after duration filter"
        );
    }

    // ================= CATEGORY FILTER =================

    public void selectCategory(String categoryText) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));

        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(categoryText.trim());

        wait.until(ExpectedConditions.visibilityOfAllElements(activityCards));

        Assert.assertTrue(
                activityCards.size() > 0,
                "No activities shown after category filter"
        );
    }

    // ================= CLEAR FILTERS =================

    public void clearDuration() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(clearDurationBtn));
        clearDurationBtn.click();

        wait.until(ExpectedConditions.visibilityOfAllElements(activityCards));

        Assert.assertTrue(
                activityCards.size() > 0,
                "Activities not visible after clearing duration"
        );
    }

    public void clearCategory() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(clearCategoryBtn));
        clearCategoryBtn.click();

        wait.until(ExpectedConditions.visibilityOfAllElements(activityCards));

        Assert.assertTrue(
                activityCards.size() > 0,
                "Activities not visible after clearing category"
        );
    }

    // ================= VALIDATION =================

    public void verifyResultCount(int expectedCount) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfAllElements(activityCards));

        Assert.assertEquals(
                activityCards.size(),
                expectedCount,
                "Activity count mismatch"
        );
    }

    // ================= SELECT ADVENTURE =================

    public void clickonAdventure(String adventureName) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        By adventureCardLink = By.xpath(
                "//h5[normalize-space()='" + adventureName + "']/ancestor::a"
        );

        WebElement cardLink =
                wait.until(ExpectedConditions.elementToBeClickable(adventureCardLink));

        cardLink.click();

        // ✅ Assertion + sync: details page loaded
        wait.until(ExpectedConditions.urlContains("adventures/detail"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("adventures/detail"),
                "Adventure details page not opened"
        );
    }

    public static void navigateToHistory() {
        
    }
}
