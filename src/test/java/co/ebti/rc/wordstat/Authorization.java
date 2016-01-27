package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class Authorization {

    private WebDriver driver;
    private Login loginPageElements;

    @BeforeMethod
    public void createDriver(){
        //driver = new FirefoxDriver();
        driver = Page.initChromeDriver();
    }

    @Test
    public void logInWithCorrectData(){
        //http://jira.lvlp.co/browse/WOR-281
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin();
        assertEquals("Check editProfile link after login", loginPageElements.editProfileLink.isDisplayed(),true);
    }

    @DataProvider
    public Object[] [] emails () {
        return new Object[][]{
                //email, error message
                {"abra@ca.ra", "wrong email or password"}
                //{"abraca", ""}
        };
    }

    @Test (dataProvider = "emails")
    public void logInWithIncorrectData(String email, String errorMessage) throws InterruptedException {
        //http://jira.lvlp.co/browse/WOR-283
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.open();
        loginPageElements.emeilField.sendKeys(email);
        loginPageElements.passwordField.sendKeys("cadabra");
        loginPageElements.loginButton.click();
        assertEquals("View error on the page after login with incorrect data", errorMessage, loginPageElements.wrongEmailOrPasswordText.getText());
        Thread.sleep(5000);
    }

    /*    @Test
    public void logInWithNonExsistsAccount(){
        //http://jira.lvlp.co/browse/WOR-284
    }*/

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
