package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    private static RemoteWebDriver driver;

    // Private constructor to prevent instantiation
    private DriverSingleton() { }

    // Get WebDriver instance
    public static RemoteWebDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(BrowserType.CHROME);
            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            driver.manage().window().maximize();
        }
        return driver;
    }

    // Quit WebDriver instance
    public static void quitDriver() {
        if (driver != null) 
            driver.quit(); 
            driver = null;  
    }
 }
