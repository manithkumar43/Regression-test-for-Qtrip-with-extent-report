package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    WebDriver driver;

    //Locators
    @FindBy(id = "floatingInput")
    WebElement email;

    @FindBy(id = "floatingPassword")
    WebElement password;

    @FindBy(className ="btn-login")
    WebElement loginButton;

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement LogOutButton;


//constructor
    public LoginPage(WebDriver driver){

        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
       PageFactory.initElements(factory, this);

    }

    public void navigateToLoginPage(){
        if(!driver.getCurrentUrl().equals(url)){
            driver.get(url);
        }
    }

    public boolean performLogin(String username, String Password){
        email.sendKeys(username);
        password.sendKeys(Password);
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Logout']")));

        boolean status= LogOutButton.isDisplayed();

        return status;

    }
}
