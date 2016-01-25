package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Categories;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

public class StopWords {

    private WebDriver driver;
    private Categories categories;
    private Login loginPageElements;
    private long start;

    private int numberOfErrors;
    private String assembledTestGroup = "http://wordstat.rc.ebti.co/groups/3263/result/anchor_file";
    private String testGroupCat = "http://wordstat.rc.ebti.co/groups/3264/result/anchor_file";
    private String testGroupSubcat = "http://wordstat.rc.ebti.co/groups/3262/result/anchor_file";
    private String swCat = "http://wordstat.rc.ebti.co/categories/1482/edit_stop_words";
    private String swSubCat = "http://wordstat.rc.ebti.co/categories/1483/edit_stop_words";
    private String swPageTestGroup1 = "http://wordstat.rc.ebti.co/groups/3262/edit_stop_words_manual";
    private String swPageTestGroup2 = "http://wordstat.rc.ebti.co/groups/3264/edit_stop_words_manual";
    private String swPageTestAssembledGroup = "http://wordstat.rc.ebti.co/groups/3263/edit_stop_words_manual";
    private String[] testCatsAndGroupsMassive = {swPageTestGroup1, swPageTestGroup2, swPageTestAssembledGroup, swCat, swSubCat};

    @BeforeSuite
    public void timeCheckStart(){
        start = System.currentTimeMillis();
    }

    @BeforeMethod
    public void createDriver() throws InterruptedException {
        driver = Page.initChromeDriver();
        //driver = new FirefoxDriver();
        //login
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin();
        loginPageElements.waitForElementVisible10Sec(loginPageElements.editProfileLink);
        loginPageElements.editProfileLink.isDisplayed();

        //clear stop words from all test categories\groups (cat, subcat, group under cat, group under subcat & assembled group under subcat)
        categories = PageFactory.initElements(driver, Categories.class);

        for(int i = 0; testCatsAndGroupsMassive.length>i; i++){
            driver.navigate().to(testCatsAndGroupsMassive[i]);
            System.out.println("Going to the page " + i + ". Link to this page:" + testCatsAndGroupsMassive[i]);

            if (categories.isElementPresentFast(categories.deleteSWPairFromLevel1)){
                System.out.println("Entering level 1");
                if(categories.isElementPresentFast(categories.deleteSWPairFromLevel2)){
                    System.out.println("Entering level 2");
                    categories.deleteSWPairFromLevel2.click();
                    Thread.sleep(500);
                    driver.switchTo().alert().accept();
                    System.out.println("Level 2 was successfully cleared");
                    Thread.sleep(500);
                }
                categories.deleteSWPairFromLevel1.click();
                Thread.sleep(500);
                driver.switchTo().alert().accept();
                System.out.println("Level 1 was successfully cleared");
                Thread.sleep(500);
                if(categories.doneButton.isDisplayed()){categories.doneButton.click();}
                Thread.sleep(500);
            }

            categories.waitForElementVisible10Sec(categories.addStopWordsButton);
            if(categories.isElementPresentFast(categories.deleteAllFromSWTextFieldButton)){
                categories.selectAllCheckbox.click();
                categories.deleteAllFromSWTextFieldButton.click();
                Thread.sleep(500);
                if(categories.doneButton.isDisplayed()){categories.doneButton.click();}
                Thread.sleep(500);
            }
            System.out.println("On page " + i + " all stop words have been cleared");
        }
        driver.navigate().to(swPageTestAssembledGroup);
        while (categories.textOnThePageContains("results: refreshing")||categories.textOnThePageContains("results: outdated")){Thread.sleep(2000);driver.navigate().refresh();}
    }

    //check in subcategory group and subcategory assembled group
    @Test
    public void addStopGoodToSubCategory() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        numberOfErrors = 0;

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "42");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "24");

        addStopWord(swSubCat, "deflection"); //-5

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "37");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "19");

        addLevelOnePair(swSubCat, "deflection", "of"); //+3

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "40");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "22");

        addLevelTwoPair(swSubCat, "evil", "gennadiy"); //-2

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "38");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "20");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors, 8);
    }

    @Test
    public void addStopGoodToMainCategory() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        numberOfErrors = 0;

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "42");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "18");

        addStopWord(swCat, "visualization"); //-5

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "37");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "13");

        addLevelOnePair(swCat, "visualization", "of"); //+3

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "40");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "16");

        addLevelTwoPair(swCat, "evil", "gennadiy"); //-2

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "38");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "14");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors, 8);
    }

    @Test
    public void addStopGoodToCatGroup() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        numberOfErrors = 0;

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "42");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "18");

        addStopWord(swPageTestGroup2, "resurrection"); //-5

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "37");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "13");

        addLevelOnePair(swPageTestGroup2, "resurrection", "of"); //+3

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "40");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "16");

        addLevelTwoPair(swPageTestGroup2, "evil", "gennadiy"); //-2

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "38");
        navigateAndCheckNumberOfKeywords("under cat", testGroupCat, "14");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors, 8);
    }

    @Test
    public void addStopGoodToSubCatGroup() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        numberOfErrors = 0;

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "42");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "24");

        addStopWord(swPageTestGroup1, "falsification"); //-5

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "37");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "19");

        addLevelOnePair(swPageTestGroup1, "falsification", "of"); //+3

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "40");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "22");

        addLevelTwoPair(swPageTestGroup1, "evil", "gennadiy"); //-2

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "38");
        navigateAndCheckNumberOfKeywords("under subCat", testGroupSubcat, "20");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors, 8);
    }

    @Test
    public void addStopGoodToAssembledGroup() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        numberOfErrors = 0;

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "42");

        addStopWord(swPageTestGroup1, "globalization"); //-5

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "37");

        addLevelOnePair(swPageTestGroup1, "globalization", "of"); //+3

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "40");

        addLevelTwoPair(swPageTestGroup1, "evil", "gennadiy"); //-2

        navigateAndCheckNumberOfKeywords("assembled", assembledTestGroup, "38");

        Page.throwExceptionIfNumberOfErrorsMoreThanZero(numberOfErrors, 4);
    }

    public void navigateAndCheckNumberOfKeywords(String group, String groupLink, String numberOfKeywords) throws InterruptedException {
        int i = 0;
        driver.navigate().to(groupLink);  //QA, тест стоп слов, Assembled
        categories.waitForElementVisible10Sec(categories.valueOfGroupKeywords);
        while (categories.textOnThePageContains("results: refreshing")||categories.textOnThePageContains("results: outdated")&&i<30){Thread.sleep(2000);driver.navigate().refresh();i++;}
        try {
            assertEquals("Check keywords in " + group + " test group", numberOfKeywords, categories.valueOfGroupKeywords.getText());
        } catch (AssertionError e){
            System.out.println(e);
            numberOfErrors++;
        }
    }

    public void addStopWord(String groupLink, String stopWord) throws InterruptedException {
        driver.navigate().to(groupLink);
        categories.waitForElementVisible10Sec(categories.swTextField);
        categories.swTextField.sendKeys(stopWord);
        categories.addStopWordsButton.click();
        Thread.sleep(500);
        if(categories.doneButton.isDisplayed()){categories.doneButton.click();}
        Thread.sleep(2000);
    }

    public void addLevelOnePair(String groupLink, String stopWord, String goodWord) throws InterruptedException {
        driver.navigate().to(groupLink);
        categories.waitForElementVisible10Sec(categories.swTextField);
        categories.goodWordsField.sendKeys(goodWord);
        categories.stopWordsField.sendKeys(stopWord);
        categories.createPairButton.click();
        Thread.sleep(500);
        if(categories.doneButton.isDisplayed()){categories.doneButton.click();}
        Thread.sleep(2000);
    }

    public void addLevelTwoPair(String groupLink, String stopWord, String goodWord) throws InterruptedException {
        driver.navigate().to(groupLink);
        categories.waitForElementVisible10Sec(categories.swTextField);
        categories.addLevel2ToSWPair.click();
        categories.addGoodWordToLevel2Pair.sendKeys(goodWord);
        categories.addStopWordToLevel2Pair.sendKeys(stopWord);
        categories.createLevel2SWPair.click();
        Thread.sleep(500);
        if(categories.doneButton.isDisplayed()){categories.doneButton.click();}
        Thread.sleep(2000);
    }
    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public void timeCheckEnd(){
        start = System.currentTimeMillis();
        System.out.println("Suite complete time: "+ (System.currentTimeMillis()-start));
    }
}
