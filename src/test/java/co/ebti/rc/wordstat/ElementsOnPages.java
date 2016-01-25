package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class ElementsOnPages {

    private WebDriver driver;
    private Login page;


    @BeforeMethod
    public void createDriver(){
        //driver = new FirefoxDriver();
        driver = Page.initChromeDriver();
        page = PageFactory.initElements(driver, Login.class);
        page.openAndLogin();
        page.editProfileLink.isDisplayed();
    }

    @Test
    public void checkMainElements(){
        assertEquals("Page contains the text 'Wordstat'", true, page.textOnThePageContains("Wordstat"));
        assertEquals("Page contains the text 'Parser settings'",true, page.textOnThePageContains("Parser settings"));
        assertEquals("Page contains the text 'Accounts'", true, page.textOnThePageContains("Accounts"));
        assertEquals("Page contains the text 'Groups'",true, page.textOnThePageContains("Groups"));
        assertEquals("Page contains the text 'Categories'", true, page.textOnThePageContains("Categories"));
        assertEquals("Page contains the text 'Collection status'",true, page.textOnThePageContains("Collection status"));
        assertEquals("Page contains the text 'Edit your profile'", true, page.textOnThePageContains("Edit your profile"));
        assertEquals("Page contains the text 'Exit'",true, page.textOnThePageContains("Exit"));
        assertEquals("Page contains the text 'revision'", true, page.textOnThePageContains("revision"));
    }

    @Test
    public void checkParserSettingsElements(){
        driver.get("http://wordstat.rc.ebti.co/seo_settings");
        assertEquals("Page contains the text 'list'", true, page.textOnThePageContains("list"));
        assertEquals("Page contains the text 'Parser backend'",true, page.textOnThePageContains("Parser backend"));
        assertEquals("Page contains the text 'seo_settings_parser_backend'", true, page.textOnThePageContains("seo_settings_parser_backend"));
        assertEquals("Page contains the text 'Save'",true, page.textOnThePageContains("Save"));
        assertEquals("Page contains the text 'cancel'", true, page.textOnThePageContains("cancel"));
    }

    @Test
    public void checkAccountsElements() throws InterruptedException {
        driver.get("http://wordstat.rc.ebti.co/accounts");
        assertEquals("Page contains the link 'User List'", true, page.textOnThePageContains("<i class=\"icon-list\"></i>"));
        assertEquals("Page contains the link 'New account'",true, page.textOnThePageContains("<i class=\"icon-plus\"></i>"));
        assertEquals("Page contains the link 'order by name'", true, page.textOnThePageContains("<a href=\"/accounts?order_by=name\">"));
        assertEquals("Page contains the link 'order by surname'",true, page.textOnThePageContains("<a href=\"/accounts?order_by=surname\"> Surname</a>"));
        assertEquals("Page contains the link 'order by email'", true, page.textOnThePageContains("<a href=\"/accounts?order_by=email\"> Email</a>"));
        assertEquals("Page contains the link 'order by role'",true, page.textOnThePageContains("<a href=\"/accounts?order_by=role\"> Role</a>"));
        assertEquals("Page contains the link 'Checkbox'", true, page.textOnThePageContains("name=\"account_ids[]"));
        assertEquals("Page contains the link 'edit account'",true, page.textOnThePageContains("edit"));
        assertEquals("Page contains the link 'delete account'", true, page.textOnThePageContains("data-confirm=\"Are you sure?\" data-method=\"delete\""));
    }

    @Test
    public void checkGroupsElements() throws InterruptedException {
        driver.get("http://wordstat.rc.ebti.co/groups");
        assertEquals("Page contains the link 'groups list'", true, page.textOnThePageContains("<a href=\"/groups\">"));
        assertEquals("Page contains the link 'create new group'",true, page.textOnThePageContains("<a href=\"/groups/new\" id=\"new\">"));
        assertEquals("Page contains the block 'Search'", true, page.textOnThePageContains("<div class=\"span12\">"));
        assertEquals("Page contains the text 'name'",true, page.textOnThePageContains("name"));
        assertEquals("Page contains the text 'weight'", true, page.textOnThePageContains("weight"));
        assertEquals("Page contains the text 'created at'",true, page.textOnThePageContains("Created"));
        assertEquals("Page contains the text 'updated at'", true, page.textOnThePageContains("Finished"));
        assertEquals("Page contains the text 'Type'",true, page.textOnThePageContains("Type"));
        assertEquals("Page contains the text 'Actions'", true, page.textOnThePageContains("Actions"));
        assertEquals("Page contains the text 'Stop Words'",true, page.textOnThePageContains("Stop"));
        assertEquals("Page contains the text 'Collection state'", true, page.textOnThePageContains("Collection state"));
        assertEquals("Page contains the text 'Started at'",true, page.textOnThePageContains("Started"));
        assertEquals("Page contains the text 'Collection duration'", true, page.textOnThePageContains("Collection duration"));
        assertEquals("Page contains the text 'Total collection time'",true, page.textOnThePageContains("Total collection time"));
        assertEquals("Page contains the text 'Collect wrong weights'",true, page.textOnThePageContains("Collect wrong weights for"));
        assertEquals("Page contains the button 'Direct: Start'", true, page.textOnThePageContains("Direct: Start"));
        assertEquals("Page contains the button 'Tasks'",true, page.textOnThePageContains("Tasks"));
        assertEquals("Page contains the button 'delete'", true, page.textOnThePageContains("<i class=\"icon-trash\"></i>"));
        assertEquals("Page contains the text 'edit'", true, page.textOnThePageContains("edit"));
    }

    @Test
    public void checkCategoriesElements(){
        assertEquals("Page contains the text 'Search'", true, page.textOnThePageContains("Search:"));
        assertEquals("Page contains the text 'Exclude'",true, page.textOnThePageContains("Exclude:"));
        assertEquals("Page contains the text 'Find'", true, page.textOnThePageContains("Find"));
        assertEquals("Page contains the text 'Create new category'",true, page.textOnThePageContains("Create new category"));
        assertEquals("Page contains the text 'RU'", true, page.textOnThePageContains("RU"));
        assertEquals("Page contains the text 'Edit status'",true, page.textOnThePageContains("Edit"));
        assertEquals("Page contains the text 'Language'", true, page.textOnThePageContains("Language"));
        assertEquals("Page contains the field 'search_category'",true, page.textOnThePageContains("name=\"search_category\""));
        assertEquals("Page contains the field 'search_category_exclude'", true, page.textOnThePageContains("name=\"search_category_exclude\""));
    }

    @Test
    public void checkCollectionStatusElements(){
        driver.get("http://wordstat.rc.ebti.co/status");
        assertEquals("Page contains the text 'A-Parser'", true, page.textOnThePageContains("A-Parser"));
        assertEquals("Page contains the text 'Service '",true, page.textOnThePageContains("Service"));
        assertEquals("Page contains the text 'Queue'", true, page.textOnThePageContains("Queue"));
        assertEquals("Page contains the text 'Value'",true, page.textOnThePageContains("Value"));
        assertEquals("Page contains the text 'Yandex Keywords'", true, page.textOnThePageContains("Yandex Keywords"));
        assertEquals("Page contains the text 'Yandex Weights'", true, page.textOnThePageContains("Yandex Weights"));
        assertEquals("Page contains the text 'Yandex Suggestions'", true, page.textOnThePageContains("Yandex Suggestions"));
        assertEquals("Page contains the text 'Google Keywords'", true, page.textOnThePageContains("Google Keywords"));
        assertEquals("Page contains the text 'Details'",true, page.textOnThePageContains("Details"));
        assertEquals("Page contains the text 'OK'",true, page.textOnThePageContains("OK"));
        assertEquals("Page contains the text 'Daily Performance (UTC)'",true, page.textOnThePageContains("Daily Performance (UTC)"));
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
