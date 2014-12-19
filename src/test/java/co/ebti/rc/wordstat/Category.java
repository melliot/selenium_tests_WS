package co.ebti.rc.wordstat;

import co.ebti.rc.wordstat.PageObjectPages.Categories;
import co.ebti.rc.wordstat.PageObjectPages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Korniev.Oleksandr on 17.12.2014.
 */
public class Category {
    private WebDriver driver;
    private Categories categories;
    private Login loginPageElements;


    @BeforeMethod
    public void createDriver(){
        driver = new FirefoxDriver();
        loginPageElements = PageFactory.initElements(driver, Login.class);
        loginPageElements.openAndLogin();
        loginPageElements.editProfileLink.isDisplayed();
    }

    @Test
    public void createNewMainCategory(){
        categories = PageFactory.initElements(driver, Categories.class);
        categories.open();
        categories.createNewCategory.isDisplayed();
        categories.createNewCategory.click();

        categories.cEcategoryName.sendKeys("QA_Autotest_Category_QA");
        categories.cErtb.sendKeys("WSCyborg");
        categories.cELanguageDropDownMenu.click();
        categories.cEChooseGreekLanguageElement.click();
        categories.cEChooseCountryDropDownMenu.click();
        categories.cEChooseBarbadosCountryElement.click();
        categories.cEsaveButton.click();

        categories.waitForElementVisible10Sec(categories.linkToQA_Autotest_Category_QA);
        categories.linkToQA_Autotest_Category_QA.isDisplayed();
        categories.linkToQA_Autotest_Category_QA.click();
    }

    @Test (dependsOnMethods = "createNewMainCategory")
    public void editMainCategory() throws InterruptedException {
        categories = PageFactory.initElements(driver, Categories.class);
        categories.open();

        //check test category and go in
        categories.linkToQA_Autotest_Category_QA.isDisplayed();
        categories.linkToQA_Autotest_Category_QA.click();
        categories.editCategory.click();

        assertEquals("Value must be, but we got: "+ categories.cEcategoryName.getAttribute("value"), true, categories.cEcategoryName.getAttribute("value").equals("QA_Autotest_Category_QA"));
        categories.cEcategoryName.clear();
        categories.cEcategoryName.sendKeys("QA_Autotest_Category_Changed_QA");

        assertEquals("Value must be, but we got: " + categories.cErtb.getAttribute("value"), true, categories.cErtb.getAttribute("value").equals("WSCyborg"));
        categories.cErtb.clear();
        categories.cErtb.sendKeys("WSEditedCyborg");

        assertEquals("Value must be, but we got: " + categories.cELanguageDropDownMenu.getText(), true, categories.cELanguageDropDownMenu.getText().equals("Modern Greek (1453-)"));
        categories.cELanguageDropDownMenu.click();
        categories.cEChooseGreekkkkkLanguageElement.click();

        assertEquals("Value must be, but we got: " + categories.cEChooseCountryDropDownMenu.getText(), true, categories.cEChooseCountryDropDownMenu.getText().equals("Barbados"));
        categories.cEChooseCountryDropDownMenu.click();
        categories.cEChooseColombiaCountryElement.click();

        categories.cEselectEngine.sendKeys("Yandex");
        categories.cEsaveButton.click();

        categories.open();
        categories.waitForElementVisible10Sec(categories.linkToQA_Autotest_Category_Changed_QA);
        categories.linkToQA_Autotest_Category_Changed_QA.isDisplayed();
        categories.linkToQA_Autotest_Category_Changed_QA.click();
        categories.editCategory.click();

        assertEquals("After changes new value must be: 'QA_Autotest_Category_Changed_QA'. but we got :" + categories.cEcategoryName.getAttribute("value"), true, categories.cEcategoryName.getAttribute("value").equals("QA_Autotest_Category_Changed_QA"));
        assertEquals("After changes new value must be: 'WSEditedCyborg'. but we got :" + categories.cErtb.getAttribute("value"), true, categories.cErtb.getAttribute("value").equals("WSEditedCyborg"));
        assertEquals("After changes new value must be: 'Ukrainian'. but we got :" + categories.cELanguageDropDownMenu.getText(), true, categories.cELanguageDropDownMenu.getText().equals("Ukrainian"));
        assertEquals("After changes new value must be: 'Colombia'. but we got :" + categories.cEChooseCountryDropDownMenu.getText(), true, categories.cEChooseCountryDropDownMenu.getText().equals("Colombia"));
        //need to find method for checking list
        //assertEquals("After changes new value must be: 'Yandex'. but we got :" + categories.cEselectEngine.getText(), true, categories.cEselectEngine.getText().equals("Yandex"));
    }

    @Test (dependsOnMethods = "editMainCategory")
    public void deleteMainCategory(){
        categories = PageFactory.initElements(driver, Categories.class);
        categories.open();

        categories.linkToQA_Autotest_Category_Changed_QA.isDisplayed();
        categories.linkToQA_Autotest_Category_Changed_QA.click();

        categories.removeCategory.isDisplayed();
        categories.removeCategory.click();
        driver.switchTo().alert().accept();

        categories.linkTextFromTR.isDisplayed();
        assertEquals("Test category was deleted, must be 'true' but we got: " + categories.linkToQA_Autotest_Category_Changed_QA, true, categories.isElementNotPresent(categories.linkToQA_Autotest_Category_Changed_QA));
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
