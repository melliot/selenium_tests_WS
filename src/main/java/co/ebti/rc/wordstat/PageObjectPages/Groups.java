package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Korniev.Oleksandr on 10.10.2014.
 */

public class Groups extends Page {
    public Groups(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "new")
    public WebElement linkToCreateNewGroup;

    @FindBy(id = "search_group")
    public WebElement searchGroup;

    @FindBy(id = "search_group_collection_type")
    public WebElement searchGroupCollectionType;

    @FindBy(id = "search_group_state")
    public WebElement searchGroupState;

    @FindBy(id = "search_group_collection_engine")
    public WebElement searchEngine;

    @FindBy(id = "search_group_with_full_responses")
    public WebElement searchWithFullResponses;

    @FindBy(id = "search_group_with_full_responses_recursive")
    public WebElement searchWithFullResponsesRecursive;

    @FindBy(id = "search_group_assembled")
    public WebElement searchOnlyAssembled;

    @FindBy(id = "search_group_invalid")
    public WebElement searchWithInvalidWeights;

    @FindBy(id = "per_page")
    public WebElement perPage;

    @FindBy(id = "appendedInputButtons")
    public WebElement searchGroupExclude;

    @FindBy(name = "commit")
    public WebElement findButton;

    @FindBy(partialLinkText = "Collect wrong weights for")
    public WebElement collectWrongWeightsButton;

    @FindBy(linkText = "/groups")
    public WebElement linkToGroups;

    //new group elements
    @FindBy(id = "group_name")
    public WebElement newGroupNameField;

    @FindBy(css = ".chosen-single>span")
    public WebElement newGroupCategory;

    @FindBy(id = "group_type")
    public WebElement newGroupAssembledOrNot;

    @FindBy(name = "commit")
    public WebElement saveNewGroupButton;

    @FindBy(linkText = "Cancel")
    public WebElement cancelNewGroupCreationButton;

    @FindBy(css = "i.icon-trash")
    public WebElement removeGroup;

    @FindBy(linkText = "QA_AutoTestAssembledGroup_QA")
    public WebElement linkTo_QA_AutoTestAssembledGroup_QA;

    @FindBy(linkText = "QA_AutoTestGroup_QA")
    public WebElement linkTo_QA_AutoTestGroup_QA;

    @FindBy(linkText = "QA_AutoTestInsideGroup_QA")
    public WebElement linkTo_QA_AutoTestInsideGroup_QA;

    @FindBy(linkText = "QA_AutoTestInsideAssembledGroup_QA")
    public WebElement linkTo_QA_AutoTestInsideAssembledGroup_QA;

    @FindBy(xpath = "//a[contains(text(),'Go to group')]")
    public WebElement goToTheNewGroup;

    @FindBy(css = "i.icon-edit")
    public WebElement editGroup;

    @FindBy(id = "delete_template")
    public WebElement resetAnchorFormButton;

    @FindBy(xpath = "//a[contains(text(),'Unique Tree')]")
    public WebElement uniqueTreeLink;

    @FindBy(xpath = "//li[contains(text(),' RU')]")
    public WebElement selectRuCategory;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/groups");
    }
}
