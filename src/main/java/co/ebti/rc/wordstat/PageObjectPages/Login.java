package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.ConfigProperties;
import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Korniev.Oleksandr on 10.10.2014.
 */

public class Login extends Page {
    public Login(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    public WebElement emeilField;

    @FindBy(id = "password")
    public WebElement passwordField;

    @FindBy(name = "commit")
    public WebElement loginButton;

    @FindBy(css = "i.icon-user")
    public WebElement editProfileLink;

    @FindBy(xpath = "//div[@id='main-content']/div")
    public WebElement wrongEmailOrPasswordText;

    //Links to Parser settings, Accounts, Groups, Categories, Collection status
    @FindBy(linkText = "Parser settings")
    public WebElement linkToParserSettings;

    @FindBy(linkText = "Accounts")
    public WebElement linkToAccounts;

    @FindBy(linkText = "Groups")
    public WebElement linkToGroups;

    @FindBy(linkText = "Categories")
    public WebElement linkToCategories;

    @FindBy(linkText = "Collection status")
    public WebElement linkToCollectionStatus;

    @Override
    public void open() {
        driver.get(HOSTNAME);
    }

    public void openWithLink(String link) {
        driver.get(HOSTNAME+link);
    }

    public void openAndLogin() {
        driver.get(HOSTNAME);
        emeilField.isDisplayed();
        emeilField.sendKeys(ConfigProperties.getProperty("testUser"));
        passwordField.sendKeys(ConfigProperties.getProperty("testPass"));
        loginButton.click();
    }

    public void openAndLogin(String email, String password) {
        driver.get(HOSTNAME);
        emeilField.isDisplayed();
        emeilField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
        editProfileLink.isDisplayed();
    }
}
