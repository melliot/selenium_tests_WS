package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Korniev.Oleksandr on 23.10.2014.
 */

public class RoleBasedAccessControl {

    private WebDriver driver;
    private Login loginPE;
    private Categories categories;
    private Groups groups;
    private ParserSettings parserSettings;
    private Accounts accountsPE;
    private ArrayList<String> testUsersEmailsAndRoles;

    @BeforeSuite
    public void createTestUsers(){
        testUsersEmailsAndRoles = addUsersAndRoles(testUsersEmailsAndRoles);

        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
        loginPE.openAndLogin();
        loginPE.editProfileLink.isDisplayed();
        accountsPE = PageFactory.initElements(driver, Accounts.class);
        accountsPE.open();

        for(int i =0,f = 0; i < 6; i++){
            if(accountsPE.textOnThePageContains(testUsersEmailsAndRoles.get(i))==true){
                System.out.println("Test user with email +"+testUsersEmailsAndRoles.get(i)+"+ already created");
                f++;
                if(f==6){System.out.println("All test users exist in the system");
                    if (driver != null) driver.quit();
                    return;}
            }
            else  {
                while (accountsPE.textOnThePageContains("John")&&accountsPE.textOnThePageContains("Smith")){
                accountsPE.deleteJohnSmith.click();
                driver.switchTo().alert().accept();
            };}
        }

        for(int i = 0; i < 6; i++) {
            accountsPE.addNewAccount();
            accountsPE.accountEmail.sendKeys(testUsersEmailsAndRoles.get(i));
            accountsPE.accountName.sendKeys(Data.nameJohn);
            accountsPE.userSurname.sendKeys(Data.surnameSmith);
            accountsPE.userPass.sendKeys(Data.password);
            accountsPE.userPassConfirm.sendKeys(Data.password);

            //drop-down list
            Select selectedElement = new Select(accountsPE.role);
            selectedElement.selectByVisibleText(testUsersEmailsAndRoles.get(i+6));

            if(testUsersEmailsAndRoles.get(i+6).equals("apiuser")||testUsersEmailsAndRoles.get(i+6).equals("developer")){
                accountsPE.userServiceName.sendKeys(ConfigProperties.getProperty("userServiceName"));
            }
            accountsPE.textOnThePageContains("User created");
            accountsPE.saveButton.click();
        }
        for(int i = 0; i < 6; i++) {
            assertEquals("Check that the account has been successfully created in the system", true, accountsPE.textOnThePageContains(testUsersEmailsAndRoles.get(i)));
        }
        if (driver != null) driver.quit();
    }

    public ArrayList<String> addUsersAndRoles(ArrayList<String> testUsersEmailsAndRoles){
        testUsersEmailsAndRoles = new ArrayList<String>();
        testUsersEmailsAndRoles.add(0,Data.developerEmail);
        testUsersEmailsAndRoles.add(1,Data.seoAdminEmail);
        testUsersEmailsAndRoles.add(2,Data.seoEmail);
        testUsersEmailsAndRoles.add(3,Data.managerEmail);
        testUsersEmailsAndRoles.add(4,Data.guestEmail);
        testUsersEmailsAndRoles.add(5,Data.apiUserEmail);
        testUsersEmailsAndRoles.add(6, "developer");
        testUsersEmailsAndRoles.add(7, "seoadmin");
        testUsersEmailsAndRoles.add(8, "seo");
        testUsersEmailsAndRoles.add(9, "manager");
        testUsersEmailsAndRoles.add(10, "guest");
        testUsersEmailsAndRoles.add(11, "apiuser");
        return testUsersEmailsAndRoles;
    }

    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
    }

    @DataProvider
    public Object[] [] userEmails () {
        return new Object[][]{
                //email, error message
                {testUsersEmailsAndRoles.get(0)},
                {testUsersEmailsAndRoles.get(1)},
                {testUsersEmailsAndRoles.get(2)},
                {testUsersEmailsAndRoles.get(3)},//manager
                {testUsersEmailsAndRoles.get(4)},
                {testUsersEmailsAndRoles.get(5)}
        };
    }

    @Test (dataProvider = "userEmails")
    public void allUsersCanSeeCategoryPageElements(String email) throws InterruptedException {
        loginPE.openAndLogin(email, Data.password);
        categories = PageFactory.initElements(driver, Categories.class);
        categories.find.isDisplayed();
        categories.ruCategory.isDisplayed();
        categories.searchCategory.isDisplayed();
        categories.searchCategoryExclude.isDisplayed();
        categories.linkTextFromTR.isDisplayed();
        if(email.equals(Data.managerEmail)){
            assertEquals("Manager don't have access to create, edit, delete categories buttons",true, categories.isElementNotPresent(categories.editRuCategory));
            assertEquals("Manager don't have access to create, edit, delete categories buttons",true, categories.isElementNotPresent(categories.createNewCategory));
            return;} //manager don't have access to create\edit\delete category, so only upper part of the test for him

        categories.createNewCategory.isDisplayed();
        categories.editRuCategory.isDisplayed();
        categories.editRuCategory.click();
        categories.cEcategoryName.isDisplayed();
        categories.cEparentDropDownList.isDisplayed();
        categories.cEcancelButton.isDisplayed();
        categories.cErtb.isDisplayed();
        categories.cEsaveButton.isDisplayed();
        categories.cEselectEngine.isDisplayed();
    }

    @Test (dataProvider = "userEmails")
    public void usersCanSeeParserSettingsPageElements(String email){
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToCategories.isDisplayed();

        //Check that manager and guest don't have access to Parser settings and stops tests for them
        if(email.equals(Data.managerEmail)||email.equals(Data.guestEmail)){
            assertEquals("Manager and Guest don't have access to parser settings elements", false, loginPE.isElementPresent(loginPE.linkToParserSettings));
            return;
        }

        parserSettings = PageFactory.initElements(driver, ParserSettings.class);
        parserSettings.open();
        parserSettings.seoSettingsParserBackend.isDisplayed();
        parserSettings.saveButton.isDisplayed();
        parserSettings.cancelButton.isDisplayed();
    }

    @Test (dataProvider = "userEmails")
    public void usersCanSeeAccountsPageElements(String email){
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToCategories.isDisplayed();

        if(email.equals(Data.developerEmail)||email.equals(Data.seoAdminEmail)){
            assertEquals("Developer and SEOAdmin have access to account settings elements", true, loginPE.isElementPresent(loginPE.linkToAccounts));
            accountsPE = PageFactory.initElements(driver, Accounts.class);
            accountsPE.addNewAccount();
            accountsPE.saveButton.isDisplayed();
            accountsPE.userServiceName.isDisplayed();
            accountsPE.accountName.isDisplayed();
            return;
        }

        assertEquals("Access to accounts have only Developer and SEOAdmin", true, accountsPE.isElementNotPresent(loginPE.linkToAccounts));
    }

    @Test (dataProvider = "userEmails")
    public void usersCanSeeGroupsSearch(String email){
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        groups.searchGroup.isDisplayed();
        groups.searchGroupExclude.isDisplayed();
        groups.searchGroupState.isDisplayed();
        groups.searchGroupCollectionType.isDisplayed();

/*        if(email.equals(Data.developerEmail)||email.equals(Data.seoAdminEmail)){
            groups.linkToCreateNewGroup.isDisplayed();
        }*/
    }

    @Test (dataProvider = "userEmails")
    public void editProfile(String email){
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        accountsPE = PageFactory.initElements(driver, Accounts.class);
        accountsPE.editProfileLink.click();
        accountsPE.accountName.isDisplayed();
        accountsPE.userSurname.isDisplayed();
        accountsPE.accountEmail.isDisplayed();
        accountsPE.userPass.isDisplayed();
        accountsPE.userPassConfirm.isDisplayed();
        accountsPE.role.isDisplayed();
        accountsPE.userServiceName.isDisplayed();
        if (email.equals(Data.apiUserEmail)||email.equals(Data.developerEmail)||email.equals(Data.seoAdminEmail)){
            assertEquals("Access to API Key have only Developer,SEOAdmin and API user", true, accountsPE.apiKeyLink.isDisplayed());
            accountsPE.apiKeyLink.click();
            accountsPE.generateAPIKey.isDisplayed();
            accountsPE.generateAPIKey.click();
            accountsPE.user_secret_token.isDisplayed();
        }
        if (email.equals(Data.guestEmail)||email.equals(Data.managerEmail)||email.equals(Data.seoEmail)) {
            try {
            accountsPE.apiKeyLink.isDisplayed();}
            catch (NoSuchElementException e){assertEquals(true, e.toString().contains("org.openqa.selenium.NoSuchElementException: Unable to locate element: {\"method\":\"partial link text\",\"selector\":\"API key\"}"));}
        }
    }

    @Test (dataProvider = "userEmails")
    public void usersCanSeeGroupsPage(String email){
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        groups.searchGroup.isDisplayed();
        assertEquals("Groups page contains button collect wrong weights",groups.textOnThePageContains("Collect wrong weights for"),true);
        groups.searchGroupCollectionType.isDisplayed();
        groups.perPage.isDisplayed();
        groups.find.isDisplayed();
        if (email.equals(Data.managerEmail)==true||email.equals(Data.guestEmail)){
            try {
                assertEquals("Manager and Guest role can create new group",false, groups.linkToCreateNewGroup.isDisplayed());

            } catch (NoSuchElementException e){
                assertEquals("Manager and Guest role can't create new group. Visibility of create new group link", false, e.toString().contains("Unable to locate element: {\"method\":\"id\",\"selector\":\"new\"}"));}
        }
        groups.linkToCreateNewGroup.isDisplayed();
        groups.linkToCreateNewGroup.click();
        groups.newGroupNameField.isDisplayed();
        groups.newGroupNameField.sendKeys("check");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }


    @AfterSuite(alwaysRun=true)
    public void deleteTestUsers(){
        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
        loginPE.openAndLogin();
        loginPE.editProfileLink.isDisplayed();
        accountsPE = PageFactory.initElements(driver, Accounts.class);
        accountsPE.open();

        while (accountsPE.textOnThePageContains("John")&&accountsPE.textOnThePageContains("Smith")){
            accountsPE.deleteJohnSmith.click();
            driver.switchTo().alert().accept();
        }
        if (driver != null) driver.quit();
    }
}