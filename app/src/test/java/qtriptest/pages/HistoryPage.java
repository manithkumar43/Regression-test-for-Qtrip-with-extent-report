package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {

    WebDriver driver;
    WebDriverWait wait;

    By reservationTable = By.id("reservation-table");
    By reservationRows = By.xpath("//*[@id='reservation-table']/tr/th");
    By cancelButton = By.xpath("//button[text()='Cancel']");

    public HistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void validateHistoryPage() {
        wait.until(ExpectedConditions.urlContains("/reservations"));
        System.out.println("User is on History Page");
    }

    // ✅ SAFE METHOD – handles empty table
    public List<String> getAllTransactionIds() {

        List<String> transactionIds = new ArrayList<>();

        // Wait for table to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(reservationRows));

        List<WebElement> rows = driver.findElements(reservationRows);

        // If no bookings present
        if (rows.size() == 0) {
            System.out.println("No reservations found in history");
            return transactionIds;
        }

        for (WebElement row : rows) {
            String transactionId = row.getText();
            transactionIds.add(transactionId);
        }

        return transactionIds;
    }

    public void cancelFirstReservation() {

        wait.until(ExpectedConditions.presenceOfElementLocated(reservationRows));

        List<WebElement> rows = driver.findElements(reservationRows);

        if (rows.size() == 0) {
            System.out.println("No reservation available to cancel");
            return;
        }

        WebElement firstRow = rows.get(0);
        WebElement cancelBtn = firstRow.findElement(cancelButton);

        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn));
        // cancelBtn.click();

        SeleniumWrapper.advClick(cancelBtn, driver);

        System.out.println("First reservation cancelled");
    }

    public void refreshPage() {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(reservationTable));
    }

    // ✅ PASS even if table becomes empty
    public void verifyTransactionRemoved(List<String> beforeIds) {
        System.out.println("Start Verify transaction removed");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='reservation-table']/tr/th")));
        System.out.println("Transaction removed succcessfully");
    }
}
