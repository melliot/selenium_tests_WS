package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(linkText = "Edit Stop Words")
    public WebElement editSW;

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

    @FindBy(css = "#category_se_settings_attributes_language_chosen > a.chosen-single > span")
    public WebElement cELanguageDropDownMenuCSSLink;

    @FindBy(id = "category_se_settings_attributes_country")
    public WebElement cEChooseCountryDropDownMenu;

    @FindBy(css = "#category_se_settings_attributes_country_chosen > a.chosen-single > span")
    public WebElement cECountryDropDownMenuCSSLink;

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

    //Edit stop words
    @FindBy(id = "word")
    public WebElement swTextField;

    @FindBy(name = "commit")
    public WebElement addStopWordsButton;

    @FindBy(className = "btn btn-success generate_morphology_button")
    public WebElement generateMorphologyButton;

    @FindBy(id = "select-all")
    public WebElement selectAllCheckbox;

    //block "With Selected:" buttons
    @FindBy(css = "button.btn.btn-danger")
    public WebElement deleteAllFromSWTextFieldButton;

    @FindBy(css = "div.btn.js-stop-words-to-form")
    public WebElement toNewGoodStopPair;

    //also we need to add buffer button

    //Good/Stop Pairs

    @FindBy(xpath = "(//a[contains(text(),'Delete')])[2]")
    public WebElement deleteSWPairFromLevel2;

    @FindBy(css = "a.btn.btn-danger")
    public WebElement deleteSWPairFromLevel1;

    @FindBy(id = "filter_push_words")
    public WebElement goodWordsField;

    @FindBy(id = "filter_stop_words")
    public WebElement stopWordsField;

    @FindBy(css = "td > input[name=\"commit\"]")
    public WebElement createPairButton;

    @FindBy(css = "div.js-add-other-level > #new_filter > #filter_stop_words")
    public WebElement addStopWordToLevel2Pair;

    @FindBy(css = "div.js-add-other-level > #new_filter > #filter_push_words")
    public WebElement addGoodWordToLevel2Pair;

    @FindBy(css = "div.js-add-other-level > #new_filter > table > tbody > tr > td > input[name=\"commit\"]")
    public WebElement createLevel2SWPair;

    @FindBy(css = "div.filter-holder > div.btn")
    public WebElement addLevel2ToSWPair;

    @FindBy(css = "div.btn.js-fill-from-selected")
    public WebElement fillFromSelectedButton;

    @FindBy(css = "input.btn")
    public WebElement doneButton;

    //for SW tests, check quantity of keywords on anchor page
    @FindBy(css = "span.badge.badge-success")
    public WebElement valueOfGroupKeywords;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/categories");
    }
}
