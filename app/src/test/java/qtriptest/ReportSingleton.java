package qtriptest;

import java.io.File;
import java.io.IOException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ReportSingleton {
    static RemoteWebDriver driver;
    static ExtentReports report;
    static ExtentTest test;


    private ReportSingleton() {

    }

    public static ExtentReports getReport() {

        if (report == null) {

            // 🔥 Ensure folder exists
            new File(System.getProperty("user.dir") + "/Reports").mkdirs();

            report = new ExtentReports(
                    System.getProperty("user.dir")
                            + "/Reports/QTRIPExecutionReport.html",
                    true
            );

            try {
                report.loadConfig(new File(System.getProperty("user.dir")+"/extent_customization_configs.xml"));
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Failed to load ExtentReports XML config: " + e.getMessage());
            }
        }
        return report;
    }

    public static String capture() throws IOException{
        File  src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        File dest = new File(System.getProperty("user.dir")+"/QkartImages/"+System.currentTimeMillis()+".png");
        FileUtils.copyFile(src, dest);
        return dest.getAbsolutePath().toString();
    }



}
