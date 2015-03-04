package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Korniev.Oleksandr on 10.10.2014.
 */

public class Categories extends Page {
    public Categories(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "search_category")
    public WebElement searchCategory;

    @FindBy(id = "search_category_exclude")
    public WebElement searchCategoryExclude;

    @FindBy(name = "commit")
    public WebElement find;

    @FindBy(xpath = "//a[contains(text(),'Create new category')]")
    public WebElement createNewCategory;

    @FindBy(linkText = "RU")
    public WebElement ruCategory;

    @FindBy(linkText = "Edit")
    public WebElement editCategory;

    @FindBy(linkText = "filmler (movies)")
    public WebElement linkTextFromTR;

    //Create/Edit category elements
    @FindBy(id = "category_name")
    public WebElement cEcategoryName;


    @FindBy(xpath = "//a[contains(text(),'Add Subcategory')]")
    public WebElement addSubCategoryLink;

    @FindBy(id = "category_parent_id")
    public WebElement cEparentDropDownList;

    //group_category_id_chosen
    @FindBy(id = "category_se_settings_attributes_language")
    public WebElement cELanguageDropDownMenu;

    @FindBy(id = "category_se_settings_attributes_country")
    public WebElement cEChooseCountryDropDownMenu;

    @FindBy(id = "category_rtb_name")
    public WebElement cErtb;

    @FindBy(id = "category_se_settings_attributes_engine")
    public WebElement cEselectEngine;

    @FindBy(name = "commit")
    public WebElement cEsaveButton;

    @FindBy(linkText = "Cancel")
    public WebElement cEcancelButton;

    @FindBy(id = "category_se_settings_attributes_engine")
    public WebElement cEEngine;

    @FindBy(linkText = "QA_Autotest_Category_QA")
    public WebElement linkToQA_Autotest_Category_QA;

    @FindBy(linkText = "QA_Autotest_Category_Changed_QA")
    public WebElement linkToQA_Autotest_Category_Changed_QA;

    //inside category
    @FindBy(xpath = "//a[contains(text(),'Remove Category')]")
    public WebElement removeCategory;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/categories");
    }
}
