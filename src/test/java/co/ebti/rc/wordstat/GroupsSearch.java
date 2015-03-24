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

        //state 'Finished' & type 'Direct'
        checkTypeAndState("Finished", "Direct");
        checkTypeAndState("Finished", "Recursive");
        checkTypeAndState("Finished", "Repeated");
        checkTypeAndState("Finished", "Supplemented");
        checkTypeAndState("Finished", "Suggestions");

        //state 'Working' & type 'Direct'
        checkTypeAndState("Working", "Direct");
        checkTypeAndState("Working", "Recursive");
        checkTypeAndState("Working", "Repeated");
        checkTypeAndState("Working", "Supplemented");
        checkTypeAndState("Working", "Suggestions");

        //state 'Idling' & type 'Direct'
        checkTypeAndState("Idling", "Direct");
        checkTypeAndState("Idling", "Recursive");
        checkTypeAndState("Idling", "Repeated");
        checkTypeAndState("Idling", "Supplemented");
        checkTypeAndState("Idling", "Suggestions");

        throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors);
    }

    @Test
    public void searchByType(){
        numberOfErrors = 0;
        filterByType("Direct");
        filterByType("Recursive");
        filterByType("Supplemented");
        filterByType("Suggestions");
        filterByType("Repeated");
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

        //Check search without parameter
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
        numberOfErrors = 0;
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

    public void searchForErrorOnThePageAndWriteErrorToLog(String errorMessages){
        if(true==groupsPageElements.textOnThePageContains(errorMessage)){
            numberOfErrors++;
            System.out.println(errorMessages);
        }
    }

    public void filterByType(String collectionType){
        Select selectCollectionType = new Select(groupsPageElements.searchGroupCollectionType);

        groupsPageElements.open();
        selectCollectionType.selectByVisibleText(collectionType);
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filter by type: " + collectionType);
    }

    public void checkTypeAndState(String state, String type){
        Select selectCollectionType = new Select(groupsPageElements.searchGroupCollectionType);
        Select selectState = new Select(groupsPageElements.searchGroupState);
        selectState.selectByVisibleText(state);
        selectCollectionType.selectByVisibleText(type);
        groupsPageElements.findButton.click();
        searchForErrorOnThePageAndWriteErrorToLog("'We're sorry' error when used filtered by type: " + type + " & state: " + state);
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
