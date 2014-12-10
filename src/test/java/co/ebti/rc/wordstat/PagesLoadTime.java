package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Categories;
import co.ebti.rc.wordstat.PageObjectPages.Groups;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by Korniev.Oleksandr on 10.10.2014.
 */

public class PagesLoadTime {

    private Login loginPageElements;
    private Categories categoriesPageElements;
    private Groups groupsPageElements;
    private WebDriver driver;

    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin();
    }

    @Test
    public void loginPageLoadTime(){
        int counter = 0;
        int result = 0;
        loginPageElements.linkToLogout.click();
        loginPageElements.emeilField.isDisplayed();
        while (counter<20) {
            //Open page and start timer
            loginPageElements.open();
            long start = System.currentTimeMillis();

            //Check elements on the page
            loginPageElements.isElementPresent(loginPageElements.emeilField);
            loginPageElements.isElementPresent(loginPageElements.passwordField);
            loginPageElements.isElementPresent(loginPageElements.loginButton);

            //Stop timer and print result
            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;
            //System.out.println("Login load time = " + timeConsumedMillis + " ms");
            result+=timeConsumedMillis;
            counter++;
        }
        System.out.println("Average login page load time = " + result/20000.0 + " sec");
    }

    @Test
    public void categoryPageLoadTime() throws InterruptedException {
        categoriesPageElements = PageFactory.initElements(driver, Categories.class);
        categoriesPageElements.waitForElementVisible10Sec(categoriesPageElements.createNewCategory);

        //Go to the Categories
        //Start page load time test
        int counter = 0;
        int result = 0;
        while (counter<10){
            categoriesPageElements.open();
            long start = System.currentTimeMillis();

            //check elements
            categoriesPageElements.isElementPresent(categoriesPageElements.createNewCategory);
            categoriesPageElements.isElementPresent(categoriesPageElements.find);
            categoriesPageElements.isElementPresent(categoriesPageElements.linkTextFromTR);
            categoriesPageElements.isElementPresent(categoriesPageElements.ruCategory);
            categoriesPageElements.isElementPresent(categoriesPageElements.searchCategory);
            categoriesPageElements.isElementPresent(categoriesPageElements.searchCategoryExclude);

            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;
            result+=timeConsumedMillis;
            counter++;
        }
        System.out.println("Average category page load time = " + result/10000.0 + " sec");
    }

    @Test
    public void groupsPageLoadTime() throws InterruptedException {
        categoriesPageElements = PageFactory.initElements(driver, Categories.class);
        categoriesPageElements.waitForElementVisible10Sec(categoriesPageElements.createNewCategory);
        groupsPageElements = PageFactory.initElements(driver, Groups.class);

        //Go to the Categories
        //Start page load time test
        int counter = 0;
        int result = 0;
        while (counter<10){
            groupsPageElements.open();
            long start = System.currentTimeMillis();

            //check elements
            //groupsPageElements.isElementPresent(groupsPageElements.collectWrongWeightsButton);
            groupsPageElements.isElementPresent(groupsPageElements.linkToCreateNewGroup);

            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;
            result+=timeConsumedMillis;
            counter++;
        }
        System.out.println("Average groups page load time = " + result/10000.0 + " sec");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

}
