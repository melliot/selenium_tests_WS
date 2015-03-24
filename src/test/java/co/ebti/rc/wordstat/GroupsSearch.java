package co.ebti.rc.wordstat;

/**
 * Created by Korniev.Oleksandr on 24.03.2015.
 */

import co.ebti.rc.wordstat.PageObjectPages.Groups;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import com.thoughtworks.selenium.SeleniumException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupsSearch {
    private WebDriver driver;
    private Login loginPageElements;
    private Groups groupsPageElements;
    private String errorMessage = "We're sorry, but something went wrong.";
    private int numberOfErrors;


    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin(Data.developerEmail, Data.password);
        groupsPageElements = PageFactory.initElements(driver, Groups.class);
        groupsPageElements.open();
    }

    @Test
    public void searchByTypeAndState(){
        numberOfErrors = 0;
        Select selectCollectionType = new Select(groupsPageElements.searchGroupCollectionType);
        Select selectState = new Select(groupsPageElements.searchGroupState);

        //===============STATE 'FINISHED'==================================================
        //state 'Finished' & type 'Direct'
        selectState.selectByVisibleText("Finished");
        selectCollectionType.selectByVisibleText("Direct");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Direct' & state 'Finished'");

        //state 'Finished' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Finished");
        selectCollectionType.selectByVisibleText("Recursive");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Recursive' & state 'Finished'");

        //state 'Finished' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Finished");
        selectCollectionType.selectByVisibleText("Repeated");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Repeated' & state 'Finished'");

        //state 'Finished' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Finished");
        selectCollectionType.selectByVisibleText("Supplemented");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Supplemented' & state 'Finished'");

        //state 'Finished' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Finished");
        selectCollectionType.selectByVisibleText("Suggestions");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Suggestions' & state 'Finished'");

        //===============STATE 'WORKING'==================================================
        //state 'Working' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Working");
        selectCollectionType.selectByVisibleText("Direct");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Direct' & state 'Working'");

        //state 'Working' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Working");
        selectCollectionType.selectByVisibleText("Recursive");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Recursive' & state 'Working'");

        //state 'Working' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Working");
        selectCollectionType.selectByVisibleText("Repeated");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Repeated' & state 'Working'");

        //state 'Working' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Working");
        selectCollectionType.selectByVisibleText("Supplemented");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Supplemented' & state 'Working'");

        //state 'Working' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Working");
        selectCollectionType.selectByVisibleText("Suggestions");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Suggestions' & state 'Working'");

        //===============STATE 'IDLING'==================================================
        //state 'Idling' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Idling");
        selectCollectionType.selectByVisibleText("Direct");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Direct' & state 'Idling'");

        //state 'Idling' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Idling");
        selectCollectionType.selectByVisibleText("Recursive");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Recursive' & state 'Idling'");

        //state 'Idling' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Idling");
        selectCollectionType.selectByVisibleText("Repeated");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Repeated' & state 'Idling'");

        //state 'Idling' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Idling");
        selectCollectionType.selectByVisibleText("Supplemented");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Supplemented' & state 'Idling'");

        //state 'Idling' & type 'Direct'
        groupsPageElements.open();
        selectState.selectByVisibleText("Idling");
        selectCollectionType.selectByVisibleText("Suggestions");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filer by type: 'Suggestions' & state 'Idling'");


        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void searchByType(){
        numberOfErrors = 0;
        Select selectCollectionType = new Select(groupsPageElements.searchGroupCollectionType);

        //Filter by Direct
        selectCollectionType.selectByVisibleText("Direct");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: 'Direct'");

        //Filter by Recursive
        groupsPageElements.open();
        selectCollectionType.selectByVisibleText("Recursive");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: 'Recursive'");

        //Filter by Supplemented
        groupsPageElements.open();
        selectCollectionType.selectByVisibleText("Supplemented");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: 'Supplemented'");

        //Filter by Repeated
        groupsPageElements.open();
        selectCollectionType.selectByVisibleText("Repeated");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: 'Repeated'");

        //Filter by Suggestions
        groupsPageElements.open();
        selectCollectionType.selectByVisibleText("Suggestions");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: 'Suggestions'");

        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void simpleAndExcludeSearch(){
        numberOfErrors = 0;

        //Search field
        groupsPageElements.searchGroup.sendKeys("Doom, QA");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used only 'search' field");

        //Exclude field
        groupsPageElements.open();
        groupsPageElements.searchGroupExclude.sendKeys("Doom, QA");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used only 'exclude' field");

        //Search & Exclude field
        groupsPageElements.open();
        groupsPageElements.searchGroupExclude.sendKeys("Doom, QA");
        groupsPageElements.searchGroup.sendKeys("games");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used only 'search' & 'exclude' field");

        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void searchUsingParametersCheckbox(){
        numberOfErrors = 0;

        //searchWithInvalidWeights
        groupsPageElements.searchWithInvalidWeights.click();
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with checkbox 'searchWithInvalidWeights'");

        //searchOnlyAssembled
        groupsPageElements.open();
        groupsPageElements.searchOnlyAssembled.click();
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with checkbox 'searchOnlyAssembled'");

        //searchWithFullResponsesRecursive
        groupsPageElements.open();
        groupsPageElements.searchWithFullResponsesRecursive.click();
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with checkbox 'searchWithFullResponsesRecursive'");

        //searchWithFullResponses
        groupsPageElements.open();
        groupsPageElements.searchWithFullResponses.click();
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with checkbox 'searchWithFullResponses'");

        //searchWithFullResponses & searchWithFullResponsesRecursive & searchOnlyAssembled & searchWithInvalidWeights
        groupsPageElements.open();
        groupsPageElements.searchWithFullResponses.click();
        groupsPageElements.searchWithFullResponsesRecursive.click();
        groupsPageElements.searchOnlyAssembled.click();
        groupsPageElements.searchWithInvalidWeights.click();
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with checkbox 'searchWithFullResponses'");


        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void searchEngine(){
        numberOfErrors = 0;
        Select selectEngine = new Select(groupsPageElements.searchEngine);

        //Check clear find
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search without parameter");

        groupsPageElements.open();
        //Check find with Google
        selectEngine.selectByVisibleText("Google");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with 'Google' engine");

        groupsPageElements.open();
        //Check find with Yandex
        selectEngine.selectByVisibleText("Yandex");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with 'Yandex' engine");

        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void searchState(){
        int numberOfErrors = 0;
        Select selectState = new Select(groupsPageElements.searchGroupState);

        //Finished
        selectState.selectByVisibleText("Finished");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with 'Finished state'");

        groupsPageElements.open();
        //Working
        selectState.selectByVisibleText("Working");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with 'Working state'");

        groupsPageElements.open();
        //Idling
        selectState.selectByVisibleText("Idling");
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when search with 'Idling state'");

        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    public void throwExceptionIfNumberOfErrorsMoreThanZero(int ifNumberOfErrorsMoreThanZero){
        if(ifNumberOfErrorsMoreThanZero>0){
            throw new SeleniumException("We have " + ifNumberOfErrorsMoreThanZero + " errors. See test log.");
        }
    }

    public void searchForErrorOnThePageAndWriteErrorToLog(String errorMessage){
        if(true==groupsPageElements.textOnThePageContains(errorMessage)){
            numberOfErrors++;
            System.out.println("'We're sorry' error when search with 'Idling state'");
        }
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
