package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Accounts;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class Account {

    private WebDriver driver;
    private Login loginPageElements;
    private Accounts accountPE;

    private String email = ConfigProperties.getProperty("emailUser00");
    private String email2 = ConfigProperties.getProperty("emailUser01");
    private String name = ConfigProperties.getProperty("nameUser00");
    private String password = ConfigProperties.getProperty("passwordUser00");
    private String passwordConfirm = ConfigProperties.getProperty("passwordConfirmUser00");
    private String userSurname = ConfigProperties.getProperty("userSurnameUser00");
    private String userServiceName = ConfigProperties.getProperty("userServiceName");

    @BeforeMethod
    public void createDriver(){
        //driver = new FirefoxDriver();
        driver = Page.initChromeDriver();
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin();
        loginPageElements.editProfileLink.isDisplayed();
    }

    @Test
    public void addNewAccount(){
    //http://jira.lvlp.co/browse/WOR-298
        accountPE = PageFactory.initElements(driver, Accounts.class);
        accountPE.open();

        while(accountPE.textOnThePageContains("some@somes.lu")==true){
            accountPE.deleteTestTestovich.click();
            driver.switchTo().alert().accept();
        }


        accountPE.addNewAccount();

        accountPE.accountEmail.sendKeys(email);
        accountPE.accountName.sendKeys(name);
        accountPE.userSurname.sendKeys(userSurname);
        accountPE.userPass.sendKeys(password);
        accountPE.userPassConfirm.sendKeys(password);
        accountPE.userServiceName.sendKeys(userServiceName);

        //drop-down list
        Select selectedElement = new Select(accountPE.role);
        selectedElement.selectByVisibleText("seoadmin");

        accountPE.saveButton.click();
        assertEquals("Check that the account has been successfully created in the system", true, accountPE.textOnThePageContains(email));
    }

    @DataProvider
    public Object[] [] accountDataList () {
        return new Object[][]{
                //WOR-310 - addNewAccountWithoutFilledMandatoryFields
                // email, name, surname, pass, pass confirm, error message, about error
                {"", "", "", "", "", "9 errors", "All fields is empty, must be 9 errors"},
                {"", name, userSurname, password, passwordConfirm, "4 errors", "Without 'email' must be 4 errors"},
                {email2, "", userSurname, password, passwordConfirm, "2 errors", "Without 'name' must be 1 error"},
                {email2, name, "", password, passwordConfirm, "2 errors", "Without 'surname' must be 2 error"},
                {email2, name, userSurname, "", passwordConfirm, "4 errors", "Without 'password' must be 4 errors"},
                {email2, name, userSurname, password, "", "3 errors", "Without 'password confirm' must be 3 errors"},
                {email2.substring(0,3), name, userSurname, password, passwordConfirm, "2 error", "With email like 'som' must be 2 errors"},
                {"wstestuser@levelupers.com", name, userSurname, password, passwordConfirm+1, "3 errors", "With typed existing account email must be 3 errors"} //WOR-300
        };
    }

    @Test (dataProvider = "accountDataList")
    public void addNewAccountWithoutSomeMandatoryFields(String email, String name, String userSurname, String password, String passwordConfirm, String errorMessage, String aboutError) throws InterruptedException {

        accountPE = PageFactory.initElements(driver, Accounts.class);
        accountPE.open();

        if (email.equals("")||email.equals("wstestuser@levelupers.com")){}
        else
            assertEquals("Check that the user has not created in the system", false, accountPE.textOnThePageContains(email));

        accountPE.addNewAccount();
        accountPE.accountEmail.sendKeys(email);
        accountPE.accountName.sendKeys(name);
        accountPE.userSurname.sendKeys(userSurname);
        accountPE.userPass.sendKeys(password);
        accountPE.userPassConfirm.sendKeys(passwordConfirm);
        accountPE.saveButton.click();
        Thread.sleep(3000);
        assertEquals(aboutError, true, accountPE.textOnThePageContains(errorMessage));
    }

    @Test
    public void deleteOtherAccount() throws InterruptedException {
        //http://jira.lvlp.co/browse/WOR-306
        accountPE = PageFactory.initElements(driver, Accounts.class);
        accountPE.open();

        assertEquals("Check that the user has created in the system", true, accountPE.textOnThePageContains(email));
        accountPE.deleteTestTestovich.click();
        driver.switchTo().alert().accept();
    }

    @DataProvider
    public Object[] [] changeEmailTests () {
        return new Object[][]{
                //WOR-310 - addNewAccountWithoutFilledMandatoryFields
                // pass, pass confirm, error message, about error
                {"123", "qwe", "2 errors", "userPass = 123, userPassConfirm =qwe, must throw 2 errors"},
                {"1233", "1234", "1 error", "userPass = 1234, userPassConfirm =1234, must throw 1 errors"},
                {"123", "124", "2 errors", "userPass = 123, userPassConfirm =124, must throw 1 error"},
                {"12345", "12346", "1 error", "userPass = 12345, userPassConfirm =12346, must throw 1 error"}
        };
    }

    @Test (dataProvider = "changeEmailTests")
    public void changeEmail(String password, String passwordConfirm, String errorMessage, String aboutError) throws InterruptedException {
        //http://jira.lvlp.co/browse/WOR-302
        accountPE = PageFactory.initElements(driver, Accounts.class);

        accountPE.editProfileLink.click();
        accountPE.userPass.isDisplayed();
        accountPE.userPass.sendKeys(password);
        accountPE.userPassConfirm.sendKeys(passwordConfirm);
        accountPE.saveButton.click();
        Thread.sleep(500);
        assertEquals(aboutError, true, accountPE.textOnThePageContains(errorMessage));
    }

    @Test
    public void changeNameAndSurname(){
        //http://jira.lvlp.co/browse/WOR-301
        accountPE = PageFactory.initElements(driver, Accounts.class);
        accountPE.editProfileLink.isDisplayed();
        accountPE.editProfileLink.click();

        String randomName = String.valueOf(Math.random());
        String randomSurname = String.valueOf(Math.random());

        accountPE.accountName.clear();
        accountPE.accountName.sendKeys("QA "+randomName);
        accountPE.userSurname.clear();
        accountPE.userSurname.sendKeys("QA "+randomSurname);
        accountPE.saveButton.click();

        assertEquals("After saving changes in user account user must see message 'User updated'", true, accountPE.textOnThePageContains("User updated"));
        accountPE.editProfileLink.click();
        assertEquals("Check that new user name is saved", true, accountPE.textOnThePageContains(randomName));
        assertEquals("Check that new user surname is saved", true, accountPE.textOnThePageContains(randomSurname));
    }

    @Test
    public void changePassword(){
        //http://jira.lvlp.co/browse/WOR-303


    }

    @Test
    public void changeRole(){
        //http://jira.lvlp.co/browse/WOR-304

    }

    @Test
    public void deleteAccountByEqualRole(){
        //http://jira.lvlp.co/browse/WOR-306

    }



    @Test
    public void deleteOwnAccount(){
        //http://jira.lvlp.co/browse/WOR-308

    }


    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
