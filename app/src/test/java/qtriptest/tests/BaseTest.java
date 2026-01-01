package qtriptest.tests;

import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import com.relevantcodes.extentreports.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class BaseTest {

    protected RemoteWebDriver driver;

    protected ExtentReports reports;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        driver= DriverSingleton.getDriver();
        reports = ReportSingleton.getReport();

        // Dynamic test name
        String testName = this.getClass().getSimpleName();
        test = reports.startTest(testName);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(LogStatus.PASS, "Test PASSES");
        } 
        else if(result.getStatus()==ITestResult.FAILURE){
            test.log(LogStatus.FAIL, result.getThrowable());
            try {
                test.log(LogStatus.FAIL, "Screeshot on Failure"+test.addScreenCapture(ReportSingleton.capture()));
                
            } catch (Exception e) {
                //TODO: handle exception
                test.log(LogStatus.FAIL, "Fail to capture Screen shot"+e.getMessage());
            }
        } 
        else if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, "Test SKIPPED");
        }

        reports.endTest(test);
        reports.flush();
        DriverSingleton.quitDriver();
    }
}
