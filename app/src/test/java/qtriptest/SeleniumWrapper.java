package qtriptest;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class SeleniumWrapper {

    // ---------------- CLICK ----------------
    public static boolean advClick(WebElement element, WebDriver driver) {
        try {
            if (element.isDisplayed()) {

                // Scroll into view
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", element);

                element.click();
                System.out.println("Button clicked");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Click failed: " + e.getMessage());
        }
        return false;
    }

    // ---------------- SEND KEYS ----------------
    public static boolean advSendKeys(WebElement inputBox, String text) {
        try {
            if (inputBox.isDisplayed()) {
                inputBox.clear();
                inputBox.sendKeys(text);
                return true;
            }
        } catch (Exception e) {
            System.out.println("SendKeys failed: " + e.getMessage());
        }
        return false;
    }

    // ---------------- NAVIGATE TO URL ----------------
    public static boolean navigateToUrl(WebDriver driver, String url) {
        try {
            if (!driver.getCurrentUrl().equals(url)) {
                driver.get(url);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Navigation failed: " + e.getMessage());
        }
        return false;
    }

    // ---------------- FIND ELEMENT WITH RETRY ----------------
    public static WebElement findElementWithRetry(WebDriver driver, By locator, int retryCount) {
        int attempts = 0;

        while (attempts < retryCount) {
            try {
                return driver.findElement(locator);
            } catch (NoSuchElementException e) {
                attempts++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }
        return null;
    }
}

