package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Group;
import co.ebti.rc.wordstat.PageObjectPages.Groups;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by Korniev.Oleksandr on 13.02.2015.
 */
public class OneGroup {

    private WebDriver driver;
    private Login page;
    private Groups groupsPageElements;
    private Group groupPageElements;
    private byte numberOfErrors;
    private String testGroupAdress = "http://wordstat.rc.ebti.co/groups/2909/result/anchor_file";
    private String getTestGroupAdressOnGroupsPage = "http://wordstat.rc.ebti.co/groups?utf8=%E2%9C%93&search_group=%D0%94%D0%9B%D0%AF+%D0%90%D0%92%D0%A2%D0%9E%D0%A2%D0%95%D0%A1%D0%A2%D0%9E%D0%92%2C+%D0%9D%D0%95+%D0%A2%D0%A0%D0%9E%D0%93%D0%90%D0%A2%D0%AC%2C+QA&search_group_exclude=&search_group_collection_engine=&search_group_collection_type=&search_group_state=&commit=Find&per_page=50";
    private String goToGroupASW = "http://wordstat.rc.ebti.co/groups/2909/edit_stop_words_from_list";

    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        page = PageFactory.initElements(driver, Login.class);
        page.openAndLogin();
        page.editProfileLink.isDisplayed();
    }


/*    @Test
    public void testTest(){
        page = PageFactory.initElements(driver, Login.class);

        open("http://wordstat.rc.ebti.co/");
        screenshot("my_file_name");
        $(page.emeilField).sendKeys(Data.developerEmail);
        $(page.passwordField).sendKeys(Data.password);
        $(page.loginButton).click();
        open("/groups");
        groupsPageElements = PageFactory.initElements(driver, Groups.class);

        $(groupsPageElements.findButton).shouldBe(Condition.visible);
    }*/



    @Test
    public void moreThen2000KeywordsCollected(){
        driver.get(testGroupAdress);
        assertTrue(page.textOnThePageContains("По некоторым задачам собрано максимальное количество результатов (2000 для yandex или 800 для google)."), "On the page user can see message about over collected keywords");
    }

    @Test
    public void moreThen2000KeywordsCollectedOnTheGroupsPage(){
        driver.get(getTestGroupAdressOnGroupsPage);
        assertTrue(page.textOnThePageContains("По некоторым задачам собрано максимальное количество результатов (2000 для yandex или 800 для google)."), "On the page user can see message about over collected keywords");
    }

    @Test
    public void regularSearchInAutomaticStopWords() {
        numberOfErrors = 0;
        driver.get(goToGroupASW);
        groupPageElements = PageFactory.initElements(driver, Group.class);
        groupPageElements.aswSearchField.isDisplayed();

        groupPageElements.aswSearchField.sendKeys("*вке");
        groupPageElements.findButton.click();
        printErrorIfFalse(page.textOnThePageContains("духовке"), "Some problem with *");
        printErrorIfFalse(page.textOnThePageContains("микроволновке"), "Some problem with *");

        groupPageElements.aswSearchField.clear();
        groupPageElements.aswSearchField.sendKeys("д*ке");
        groupPageElements.findButton.click();
        printErrorIfFalse(page.textOnThePageContains("духовке"), "Some problem with *");
        printErrorIfFalse(page.textOnThePageContains("девушке"), "Some problem with *");

        groupPageElements.aswSearchField.clear();
        groupPageElements.aswSearchField.sendKeys("...ина");
        groupPageElements.findButton.click();
        printErrorIfFalse(page.textOnThePageContains("долина"), "Some problem with .");

        groupPageElements.aswSearchField.clear();
        groupPageElements.aswSearchField.sendKeys("................");
        groupPageElements.findButton.click();
        printErrorIfFalse(page.textOnThePageContains("консервированная"), "Some problem with .");
        printErrorIfFalse(page.textOnThePageContains("консервированной"), "Some problem with .");

        groupPageElements.aswSearchField.clear();
        groupPageElements.aswSearchField.sendKeys("qweqweqeqw");
        groupPageElements.findButton.click();
        printErrorIfFalse(page.textOnThePageContains("There is nothing to show"), "There is no 'There is nothing to show message");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void checkPerPageOnAutomaticStopWords() {
        numberOfErrors = 0;
        driver.get(goToGroupASW);
        groupPageElements = PageFactory.initElements(driver, Group.class);
        groupPageElements.aswSearchField.isDisplayed();

        Select select = new Select(groupPageElements.perPageOnASW);
        printErrorIfFalse(Integer.valueOf(select.getFirstSelectedOption().getText())==50, "50");
        printErrorIfFalse(Integer.valueOf(select.getFirstSelectedOption().getText())!=350, "350");
        printErrorIfFalse(page.textOnThePageContains("речные"), "keyword#50 = 'речные");

        select.selectByValue("100");
        printErrorIfFalse(page.textOnThePageContains("медвежьи"), "keyword#100 = 'медвежьи");

        select.selectByValue("200");
        printErrorIfFalse(page.textOnThePageContains("ремень"), "keyword#200 = 'ремень");

        select.selectByValue("All");
        printErrorIfFalse(page.textOnThePageContains("комаровский"), "last keyword from 'all' = 'комаровский");

        select.selectByValue("50");
        printErrorIfFalse(page.textOnThePageContains("речные"), "keyword#50 = 'речные");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    private void printErrorIfFalse(boolean bool, String errorMessage){
        if(bool==false){
            System.out.println(errorMessage);
            numberOfErrors++;
        }
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
