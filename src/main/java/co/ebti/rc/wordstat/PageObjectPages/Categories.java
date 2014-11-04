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

    @FindBy(linkText = "Create new category")
    public WebElement createNewCategory;

    @FindBy(linkText = "RU")
    public WebElement ruCategory;

    @FindBy(linkText = "Edit")
    public WebElement editRuCategory;

    @FindBy(linkText = "filmler (movies)")
    public WebElement linkTextFromTR;

    //Create/Edit category elements
    @FindBy(id = "category_name")
    public WebElement cEcategoryName;

    @FindBy(id = "category_parent_id")
    public WebElement cEparentDropDownList;

    @FindBy(id = "category_rtb_name")
    public WebElement cErtb;

    @FindBy(id = "category__se_settings_attributes_engine")
    public WebElement cEselectEngine;

    @FindBy(name = "commit")
    public WebElement cEsaveButton;

    @FindBy(linkText = "Cancel")
    public WebElement cEcancelButton;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/categories");
    }
}
