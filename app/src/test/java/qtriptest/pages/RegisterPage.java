package qtriptest.pages;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.Format;
import java.time.LocalDateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RegisterPage {

    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    WebDriver driver;
    public String lastGeneratedUsername="";

    @FindBy(id="floatingInput")
    WebElement email;

    @FindBy(name ="password")
    WebElement password;

    @FindBy(name ="confirmpassword")
    WebElement confirmPassword;

    @FindBy(xpath = "//button[text()='Register Now']")
    WebElement RegisterButon;


    public RegisterPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
         PageFactory.initElements(factory, this);

    }

    public void navigateToRegisterPage(){
        if(!driver.getCurrentUrl().equals(url)){
            driver.get(url);
        }
    }



    public boolean performRegistration(String Username, String Password, Boolean makeUserDynamic) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String test_data_username;
    
        if (makeUserDynamic) {
            test_data_username = Username + "_" + timestamp.getTime() + "@gmail.com";
        } else {
            test_data_username = Username + "@gmail.com";
        }
    
        email.clear();
        email.sendKeys(test_data_username);
    
        password.clear();
        password.sendKeys(Password);
    
        confirmPassword.clear();
        confirmPassword.sendKeys(Password);
    
        RegisterButon.click();
    
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains("/login"));
    
        this.lastGeneratedUsername = test_data_username;
    
        return driver.getCurrentUrl().endsWith("/login");
    }
    

}
