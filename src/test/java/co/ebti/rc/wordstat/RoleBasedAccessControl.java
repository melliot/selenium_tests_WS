package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import java.awt.*;
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
    private Group group;
    private ParserSettings parserSettings;
    private Accounts accountsPE;
    private ArrayList<String> testUsersEmails;
    private ArrayList<String> testUsersRoles;
    private String groupForInsideCheck;
    private String assembledGroupForInsideCheck;



    @BeforeSuite
    public void createTestUsers() throws AWTException {
        testUsersRoles = addRoles(testUsersRoles);
        testUsersEmails = addEmails(testUsersEmails);
        int rolesNumber = 7;/*
        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
        loginPE.openAndLogin();
        loginPE.editProfileLink.isDisplayed();
        groupForInsideCheck = createTestGroupBySeoAdmin();
        assembledGroupForInsideCheck = createAssembledTestGroupBySeoAdmin();



        accountsPE = PageFactory.initElements(driver, Accounts.class);
        accountsPE.open();

        //replace for to iterator.hasNext
        for(int i =0,f = 0; i < rolesNumber; i++){
            if(accountsPE.textOnThePageContains(testUsersEmails.get(i))==true){
                System.out.println("Test user with email +"+testUsersEmails.get(i)+"+ already created");
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

        //replace for to iterator.hasNext
        for(int i = 0; i < rolesNumber; i++) {
            accountsPE.addNewAccount();
            accountsPE.accountEmail.sendKeys(testUsersEmails.get(i));
            accountsPE.accountName.sendKeys(Data.nameJohn);
            accountsPE.userSurname.sendKeys(Data.surnameSmith);
            accountsPE.userPass.sendKeys(Data.password);
            accountsPE.userPassConfirm.sendKeys(Data.password);

            //drop-down list
            Select selectedElement = new Select(accountsPE.role);
            selectedElement.selectByVisibleText(testUsersRoles.get(i));

            if(testUsersRoles.get(i).equals("apiuser")||testUsersRoles.get(i).equals("developer")){
                accountsPE.userServiceName.sendKeys(ConfigProperties.getProperty("userServiceName"));
            }
            accountsPE.textOnThePageContains("User created");
            accountsPE.saveButton.click();
        }

        //replace for to iterator.hasNext
        for(int i = 0; i < rolesNumber; i++) {
            assertEquals("Check that the account has been successfully created in the system", true, accountsPE.textOnThePageContains(testUsersEmails.get(i)));
        }
        if (driver != null) driver.quit();*/
    }

    public ArrayList<String> addRoles(ArrayList<String> addRoles){
        addRoles = new ArrayList<>();

        addRoles.add(0, "developer");
        addRoles.add(1, "seoadmin");
        addRoles.add(2, "seo");
        addRoles.add(3, "manager");
        addRoles.add(4, "guest");
        addRoles.add(5, "apiuser");
        addRoles.add(6, "seomanager");
        return addRoles;
    }

    public ArrayList<String> addEmails(ArrayList<String> addEmails){
        addEmails = new ArrayList<>();
        addEmails.add(0,Data.developerEmail);
        addEmails.add(1,Data.seoAdminEmail);
        addEmails.add(2,Data.seoEmail);
        addEmails.add(3,Data.managerEmail);
        addEmails.add(4,Data.guestEmail);
        addEmails.add(5,Data.apiUserEmail);
        addEmails.add(6, Data.seoManagerEmail);
        return addEmails;
    }

    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
    }

    @DataProvider
    public Object[] [] userEmails () {
        return new Object[][]{
                //email
                {testUsersEmails.get(0)},
                {testUsersEmails.get(1)},
                {testUsersEmails.get(2)},
                {testUsersEmails.get(3)},//manager
                {testUsersEmails.get(4)},//guest
                {testUsersEmails.get(5)},//apiuser
                {testUsersEmails.get(6)}//seomanager
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
            assertEquals("Manager don't have access to create, edit, delete categories buttons",true, categories.isElementNotPresent(categories.editCategory));
            assertEquals("Manager don't have access to create, edit, delete categories buttons",true, categories.isElementNotPresent(categories.createNewCategory));
            return;} //manager don't have access to create\edit\delete category, so only upper part of the test for him

        categories.createNewCategory.isDisplayed();
        categories.editCategory.isDisplayed();
        categories.editCategory.click();
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
        if (email.equals(Data.guestEmail)||email.equals(Data.managerEmail)||email.equals(Data.seoEmail)||email.equals(Data.seoManagerEmail)) {
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
        if(Data.developerEmail.equals(email)||Data.seoAdminEmail.equals(email)||Data.seoEmail.equals(email)||Data.apiUserEmail.equals(email)||Data.seoManagerEmail.equals(email)){
        assertEquals("Groups page contains button collect wrong weights",true, groups.textOnThePageContains("Collect wrong weights for"));}
        if(Data.managerEmail.equals(email)||Data.guestEmail.equals(email)){
            assertEquals("Groups page contains button collect wrong weights",false, groups.textOnThePageContains("Collect wrong weights for"));
        }
        groups.searchGroupCollectionType.isDisplayed();
        groups.perPage.isDisplayed();
        groups.find.isDisplayed();
    }

    @Test (dataProvider = "userEmails")
    public void usersCanCreateEditDeleteAssembledGroup(String email) throws InterruptedException {
        final String testGroupName = "QA_AutoTestAssembledGroup_QA";

        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();

        //Seek and delete if find test group.
        if(groups.textOnThePageContains(testGroupName)&&email.equals(Data.managerEmail)==false){
            deleteGroup(groups.linkTo_QA_AutoTestAssembledGroup_QA, testGroupName);
        }

        //Guest can't create new groups
        if (email.equals(Data.guestEmail)){
            try {
                assertEquals("Guest role can create new group",false, groups.linkToCreateNewGroup.isDisplayed());
            } catch (NoSuchElementException e){
                assertEquals("Guest role can't create new group", true, e.toString().contains("Unable to locate element: {\"method\":\"id\",\"selector\":\"new\"}"));}
        }
        if (email.equals(Data.guestEmail)){return;}
        groups.linkToCreateNewGroup.isDisplayed();
        groups.linkToCreateNewGroup.click();

        //Check elements
        groups.newGroupNameField.isDisplayed();
        groups.cancelNewGroupCreationButton.isDisplayed();
        groups.saveNewGroupButton.isDisplayed();

        //Create assembled group
        groups.newGroupNameField.sendKeys(testGroupName);
        groups.newGroupCategory.click();
        groups.selectRuCategory.click();

        //For 4 roles we can choose what group we want to create, 2 roles (manager) can create only assembled group so we pass this step for them
        if (email.equals(Data.developerEmail)||email.equals(Data.seoAdminEmail)||email.equals(Data.seoEmail)||email.equals(Data.apiUserEmail)||email.equals(Data.seoManagerEmail)) {
            groups.newGroupAssembledOrNot.click();
            assertEquals("Check that we have click on the AssembledGroup checkbox", true, groups.newGroupAssembledOrNot.isSelected());
        }

        //Save new group
        groups.saveNewGroupButton.isDisplayed();
        Thread.sleep(2500);
        groups.saveNewGroupButton.click();
        groups.waitForElementVisible10Sec(groups.goToTheNewGroup);
        groups.goToTheNewGroup.click();
        groups.resetAnchorFormButton.isDisplayed();

        groups.open();
        //Seek and delete if find test group.
        if(groups.textOnThePageContains(testGroupName)&&email.equals(Data.managerEmail)==false){
            deleteGroup(groups.linkTo_QA_AutoTestAssembledGroup_QA, testGroupName);
        }
    }

    //Seomanager can create only assembled group. In future we need additional negative test for simple group creation.
    @Test (dataProvider = "userEmails")
    public void usersCanCreateEditDeleteGroup(String email) throws InterruptedException {
        //if (email.equals(Data.seoManagerEmail)){throw new SkipException("Skipping the test case");}

        final String testGroupName = "QA_AutoTestGroup_QA";

        //Login to the app and go to the groups
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        //Manager and Guest can't create new groups
        if (email.equals(Data.managerEmail) ||email.equals(Data.guestEmail)){
            try {
                assertEquals("Manager and Guest role can create new group",false, groups.linkToCreateNewGroup.isDisplayed());
            } catch (NoSuchElementException e){
                System.out.println("Manager and Guest role can create new group. Visibility of create new group link" + e.toString());
            return;
            }
        }

        //Seek and delete if find test group.
        if(groups.textOnThePageContains(testGroupName)){
            deleteGroup(groups.linkTo_QA_AutoTestGroup_QA, testGroupName);
        }

        groups.linkToCreateNewGroup.isDisplayed();
        groups.linkToCreateNewGroup.click();

        //Element check, then add some info and cancel. After canceling say 'ok' to popup window
        groups.newGroupNameField.isDisplayed();
        groups.cancelNewGroupCreationButton.isDisplayed();
        groups.saveNewGroupButton.isDisplayed();
        groups.newGroupNameField.sendKeys(testGroupName);
        groups.cancelNewGroupCreationButton.click();
        driver.switchTo().alert().accept();
        groups.waitForElementVisible10Sec(groups.searchGroup);

        //Create new group
        groups.linkToCreateNewGroup.click();
        groups.newGroupNameField.sendKeys(testGroupName);
        groups.textOnThePageContains("can't be blank");
        groups.saveNewGroupButton.isDisplayed();

        //choose ru category
        groups.newGroupCategory.click();
        groups.selectRuCategory.click();

        //Save new group
        groups.saveNewGroupButton.click();
        groups.waitForElementVisible10Sec(groups.goToTheNewGroup);
        groups.goToTheNewGroup.click();
        groups.resetAnchorFormButton.isDisplayed();

        //Edit group
        groups.open();
        groups.linkTo_QA_AutoTestGroup_QA.isDisplayed();
        groups.linkTo_QA_AutoTestGroup_QA.click();
        groups.editGroup.click();
        groups.newGroupNameField.isDisplayed();
        groups.newGroupCategory.isDisplayed();

        //DeleteCreatedGroup
        deleteGroup(groups.linkTo_QA_AutoTestGroup_QA, testGroupName);
    }

    @Test (dataProvider = "userEmails")
    public void userCanSeeTabsWithinTheGroup(String email) throws AWTException {
        //Login to the app and go to the groups
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();

        //Go to test group
        groups.linkTo_QA_AutoTestInsideGroup_QA.isDisplayed();
        groups.linkTo_QA_AutoTestInsideGroup_QA.click();
        groups.resetAnchorFormButton.isDisplayed();

        //Check tabs
        group = PageFactory.initElements(driver, Group.class);
        //For manager
        if (email.equals(Data.managerEmail)){
            group.list.isDisplayed();
            group.keywordsTree.isDisplayed();
            group.keywordsTree.click();
            group.newPresetName.isDisplayed();
            group.thematicRootPhrases.isDisplayed();
            group.addConjunctionsAndPrepositions.isDisplayed();
            group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
            group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
            group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
            group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
            group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
            group.uniqueTree.click();
            group.newPresetName.isDisplayed();
            group.thematicRootPhrases.isDisplayed();
            group.addConjunctionsAndPrepositions.isDisplayed();
            group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
            group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
            group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
            group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
            group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
            group.list.click();
            assertEquals("Manager can't see 'Anchor File'", true, group.isElementNotPresent(group.anchorFile));
            assertEquals("Manager can't see 'Import/Export'", true, group.isElementNotPresent(group.importExport));
            assertEquals("Manager can't see 'Stop Words'", true, group.isElementNotPresent(group.stopWords));
            return;
        }
        //For guest & seoManager
        if (email.equals(Data.guestEmail)) {
            assertEquals("Guest/seoManager can't see 'Import/Export' tab", true, group.isElementNotPresent(group.importExport));
            assertEquals("Guest/seoManager can't see 'Stop Words' tab", true, group.isElementNotPresent(group.stopWords));
            assertEquals("Guest/seoManager can't see 'List' tab", true, group.isElementNotPresent(group.list));
            assertEquals("Guest/seoManager can't see 'Keywords Tree' tab", true, group.isElementNotPresent(group.keywordsTree));

            assertEquals("Guest can't see 'Anchor File' tab", true, group.isElementNotPresent(group.anchorFile));
            return;
        }

        group.list.isDisplayed();
        group.importExport.isDisplayed();
        group.keywordsTree.isDisplayed();
        group.stopWords.isDisplayed();

        //check anchor file elements
        group.resetAnchorFormButton.isDisplayed(); group.templateNameField.isDisplayed();
        group.loadTemplateMenu.isDisplayed(); group.saveTemplateButton.isDisplayed();
        group.addPreposition.isDisplayed(); group.addDomain.isDisplayed();
        group.mixOneMoreGroup.isDisplayed(); group.addBuzzwords.isDisplayed();
        group.downloadButton.isDisplayed();group.previewButton.isDisplayed();
        group.searchResultExcludeField.isDisplayed(); group.searchResultField.isDisplayed();
        group.searchResultWeightFilterField.isDisplayed(); group.findButton.isDisplayed();
        group.resultsPerPageMenu.isDisplayed(); group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

        //check Keywords tree elements
        group.keywordsTree.click();
        group.newPresetName.isDisplayed();
        group.thematicRootPhrases.isDisplayed();
        group.addConjunctionsAndPrepositions.isDisplayed();
        group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
        group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
        group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
        group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
        group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
        group.uniqueTree.click();
        group.newPresetName.isDisplayed();
        group.thematicRootPhrases.isDisplayed();
        group.addConjunctionsAndPrepositions.isDisplayed();
        group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
        group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
        group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
        group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
        group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");

        //Check List elements
        group.list.click();
        group.blacklistSuggestsField.isDisplayed();
        group.updateBlacklistSuggestsButton.isDisplayed(); group.searchResultWeightFilterField.isDisplayed();
        group.searchResultField.isDisplayed(); group.searchResultExcludeField.isDisplayed();
        group.sortByExactWeight.isDisplayed(); group.resultsPerPageMenu.isDisplayed();
        group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

        //Check Import/Export
        group.importExport.click();
        group.uploadUpdate.isDisplayed(); group.uploadReplace.isDisplayed();
        group.uploadButtonIE.isDisplayed(); group.downloadButtonIE.isDisplayed();
        group.searchResultWeightFilterField.isDisplayed();
        group.searchResultField.isDisplayed(); group.searchResultExcludeField.isDisplayed();
        group.sortByExactWeight.isDisplayed(); group.resultsPerPageMenu.isDisplayed();
        group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

/*        //Stop words page elements
        group.stopWords.click();
        group.mswStopWordField.isDisplayed(); group.mswAddStopWord.isDisplayed();
        group.mswHideAllLocal.isDisplayed();
        group.automaticStopWords.click();
        group.aswAddToStopWords.isDisplayed(); group.aswRebuildRating.isDisplayed();
        group.wordsTree.click(); group.wtBuildTreeButton.isDisplayed();*/
    }

    @Test (dataProvider = "userEmails")
    public void userCanSeeTabsWithinAssembledGroup(String email) throws AWTException {
        //Login to the app and go to the groups
        loginPE.openAndLogin(email, Data.password);
        loginPE.linkToGroups.isDisplayed();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();

        //Go to test group
        groups.linkTo_QA_AutoTestInsideAssembledGroup_QA.isDisplayed();
        groups.linkTo_QA_AutoTestInsideAssembledGroup_QA.click();
        groups.resetAnchorFormButton.isDisplayed();

        //Check tabs
        group = PageFactory.initElements(driver, Group.class);
        //For manager
        if (email.equals(Data.managerEmail)){
            group.list.isDisplayed();
            group.keywordsTree.isDisplayed();
            group.keywordsTree.click();
            group.newPresetName.isDisplayed();
            group.thematicRootPhrases.isDisplayed();
            group.addConjunctionsAndPrepositions.isDisplayed();
            group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
            group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
            group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
            group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
            group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
            group.uniqueTree.click();
            group.newPresetName.isDisplayed();
            group.thematicRootPhrases.isDisplayed();
            group.addConjunctionsAndPrepositions.isDisplayed();
            group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
            group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
            group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
            group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
            group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
            group.list.click();
            assertEquals("Manager can't see 'Anchor File'", true, group.isElementNotPresent(group.anchorFile));
            assertEquals("Manager can't see 'Import/Export'", true, group.isElementNotPresent(group.importExport));
            //assertEquals("Manager can't see 'Stop Words'", true, group.isElementNotPresent(group.stopWords));
            return;
        }
        //For guest
        if (email.equals(Data.guestEmail)) {
            assertEquals("Guest/seoManager can't see 'Import/Export' tab", true, group.isElementNotPresent(group.importExport));
            assertEquals("Guest/seoManager can't see 'Stop Words' tab", true, group.isElementNotPresent(group.stopWords));
            assertEquals("Guest/seoManager can't see 'List' tab", true, group.isElementNotPresent(group.list));
            assertEquals("Guest/seoManager can't see 'Keywords Tree' tab", true, group.isElementNotPresent(group.keywordsTree));
            assertEquals("Guest can't see 'Anchor File' tab", true, group.isElementNotPresent(group.anchorFile));
            return;
        }

        group.list.isDisplayed();
        group.importExport.isDisplayed();
        group.keywordsTree.isDisplayed();
        group.stopWords.isDisplayed();

        //check anchor file elements
        group.resetAnchorFormButton.isDisplayed(); group.templateNameField.isDisplayed();
        group.loadTemplateMenu.isDisplayed(); group.saveTemplateButton.isDisplayed();
        group.addPreposition.isDisplayed(); group.addDomain.isDisplayed();
        group.mixOneMoreGroup.isDisplayed(); group.addBuzzwords.isDisplayed();
        group.downloadButton.isDisplayed();group.previewButton.isDisplayed();
        group.searchResultExcludeField.isDisplayed(); group.searchResultField.isDisplayed();
        group.searchResultWeightFilterField.isDisplayed(); group.findButton.isDisplayed();
        group.resultsPerPageMenu.isDisplayed(); group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

        //check Keywords tree elements
        group.keywordsTree.click();
        group.newPresetName.isDisplayed();
        group.thematicRootPhrases.isDisplayed();
        group.addConjunctionsAndPrepositions.isDisplayed();
        group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
        group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
        group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
        group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
        group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");
        group.uniqueTree.click();
        group.newPresetName.isDisplayed();
        group.thematicRootPhrases.isDisplayed();
        group.addConjunctionsAndPrepositions.isDisplayed();
        group.textOnThePageContains("name"); group.textOnThePageContains("generation"); group.textOnThePageContains("aliases");
        group.textOnThePageContains("created"); group.textOnThePageContains("permutations & morphology");
        group.textOnThePageContains("manual changes"); group.textOnThePageContains("rollback");
        group.textOnThePageContains("Keywords tree settings"); group.textOnThePageContains("Buzzwords");
        group.textOnThePageContains("Aliases:"); group.textOnThePageContains("Thematic root phrases::");

        //Check List elements
        group.list.click();
        group.searchResultWeightFilterField.isDisplayed();
        group.searchResultField.isDisplayed(); group.searchResultExcludeField.isDisplayed();
        group.sortByExactWeight.isDisplayed(); group.resultsPerPageMenu.isDisplayed();
        group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

        //Check Import/Export
        group.importExport.click(); group.downloadButtonIE.isDisplayed();
        group.searchResultWeightFilterField.isDisplayed();
        group.searchResultField.isDisplayed(); group.searchResultExcludeField.isDisplayed();
        group.sortByExactWeight.isDisplayed(); group.resultsPerPageMenu.isDisplayed();
        group.toggleAllSuggests.isDisplayed();
        group.sortByCollectionType.isDisplayed(); group.sortByExactWeight.isDisplayed();
        group.sortByFullExactWeight.isDisplayed(); group.sortByWeight.isDisplayed();

/*        //Stop words page elements
        group.stopWords.click();
        group.mswStopWordField.isDisplayed(); group.mswAddStopWord.isDisplayed();
        group.mswHideAllLocal.isDisplayed();
        group.automaticStopWords.click();
        group.aswAddToStopWords.isDisplayed(); group.aswRebuildRating.isDisplayed();
        group.wordsTree.click(); group.wtBuildTreeButton.isDisplayed();*/
    }

    public String createTestGroupBySeoAdmin() throws AWTException {
        String testGroupName = "QA_AutoTestInsideGroup_QA";
        //Create new group steps
        //1. Login to the app, go to the groups and click to create new group
/*        loginPE.openAndLogin(Data.seoAdminEmail, Data.password);
        loginPE.linkToGroups.isDisplayed();*/
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        groups.linkToCreateNewGroup.isDisplayed();
        //2. Seek and delete if find test group.
        if(groups.textOnThePageContains(testGroupName)){
            deleteGroup(groups.linkTo_QA_AutoTestInsideGroup_QA, testGroupName);
        }
        //3. Create new group
        groups.linkToCreateNewGroup.click();
        groups.saveNewGroupButton.isDisplayed();
        groups.newGroupNameField.sendKeys(testGroupName);

        //4. choose ru category
        groups.newGroupCategory.click();
        groups.selectRuCategory.click();

        //5. Save new group
        groups.saveNewGroupButton.click();
        groups.waitForElementVisible10Sec(groups.goToTheNewGroup);
        groups.goToTheNewGroup.click();
        groups.resetAnchorFormButton.isDisplayed();
        return testGroupName;
    }

    public String createAssembledTestGroupBySeoAdmin() throws AWTException {
        String testGroupName = "QA_AutoTestInsideAssembledGroup_QA";
        //Create new group steps
        //1. Login to the app, go to the groups and click to create new group
/*        loginPE.openAndLogin(Data.seoAdminEmail, Data.password);
        loginPE.linkToGroups.isDisplayed();*/
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        groups.linkToCreateNewGroup.isDisplayed();
        //2. Seek and delete if find test group.
        if(groups.textOnThePageContains(testGroupName)){
            deleteGroup(groups.linkTo_QA_AutoTestInsideAssembledGroup_QA, testGroupName);
        }
        //3. Create new group
        groups.linkToCreateNewGroup.click();
        groups.saveNewGroupButton.isDisplayed();
        groups.newGroupNameField.sendKeys(testGroupName);

        //4. Choose ru category
        groups.newGroupCategory.click();
        groups.selectRuCategory.click();

        groups.newGroupAssembledOrNot.click();
        assertEquals("Check that we have click on the AssembledGroup checkbox", true, groups.newGroupAssembledOrNot.isSelected());

        //5. Save new group
        groups.saveNewGroupButton.click();
        groups.waitForElementVisible10Sec(groups.goToTheNewGroup);
        groups.goToTheNewGroup.click();
        groups.resetAnchorFormButton.isDisplayed();
        return testGroupName;
    }

    public void deleteGroup(WebElement linkToTheGroup, String groupName){
        //groups.linkTo_QA_AutoTestGroup_QA.click();
        groups = PageFactory.initElements(driver, Groups.class);
        groups.open();
        groups.searchGroup.isDisplayed();
        linkToTheGroup.click();
        groups.resetAnchorFormButton.isDisplayed();
        groups.removeGroup.isDisplayed();
        groups.removeGroup.click();
        driver.switchTo().alert().accept();
        groups.waitForElementVisible10Sec(groups.searchGroup);
        assertEquals("Try to find deleted group in groups list", false, groups.textOnThePageContains(groupName));
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite(alwaysRun=true)
    public void deleteTestUsers(){
/*        driver = new FirefoxDriver();
        loginPE = PageFactory.initElements(driver, Login.class);
        loginPE.openAndLogin();
        loginPE.editProfileLink.isDisplayed();
        accountsPE = PageFactory.initElements(driver, Accounts.class);
        accountsPE.open();

        while (accountsPE.textOnThePageContains("John")&&accountsPE.textOnThePageContains("Smith")){
            accountsPE.deleteJohnSmith.click();
            driver.switchTo().alert().accept();
        }
        if (driver != null) driver.quit();*/
    }
}