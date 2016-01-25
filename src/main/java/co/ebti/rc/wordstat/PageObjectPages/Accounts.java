package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Accounts extends Page{
    public Accounts(WebDriver driver) {
        super(driver);
    }

    //Accounts webElements
    @FindBy(css = "a[title=\"edit Test Testovich\"] > i.icon-edit")
    public WebElement editTestTestovich;

    @FindBy(css = "a[title=\"delete Test Testovich\"] > i.icon-trash")
    public WebElement deleteTestTestovich;

    @FindBy(css = "a[title=\"edit John Smith\"] > i.icon-edit")
    public WebElement editJohnSmith;

    @FindBy(css = "a[title=\"delete John Smith\"] > i.icon-trash")
    public WebElement deleteJohnSmith;


    //New Account webElements
    @FindBy(id = "user_name")
    public WebElement accountName;

    @FindBy(id = "user_email")
    public WebElement accountEmail;

    @FindBy(id = "user_password")
    public WebElement userPass;

    @FindBy(id = "user_password_confirmation")
    public WebElement userPassConfirm;

    @FindBy(id = "user_role")
    public WebElement role;

    @FindBy(id = "user_surname")
    public WebElement userSurname;

    @FindBy(id = "user_service_name")
    public WebElement userServiceName;

    @FindBy(name = "commit")
    public WebElement saveButton;

    @FindBy(css = "i.icon-user")
    public WebElement editProfileLink;

    @FindBy(partialLinkText = "API key")
    public WebElement apiKeyLink;

    @FindBy(name = "commit")
    public WebElement generateAPIKey;

    @FindBy(id = "user_secret_token")
    public WebElement user_secret_token;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/accounts");
    }

    public void addNewAccount() {
        driver.get(HOSTNAME+"/accounts/new");
    }

}
