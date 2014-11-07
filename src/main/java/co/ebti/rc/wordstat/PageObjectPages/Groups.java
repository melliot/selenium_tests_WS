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

    @FindBy(id = "per_page")
    public WebElement perPage;

    @FindBy(id = "appendedInputButtons")
    public WebElement searchGroupExclude;

    @FindBy(name = "commit")
    public WebElement find;

    @FindBy(partialLinkText = "Collect wrong weights for")
    public WebElement collectWrongWeightsButton;



    @FindBy(linkText = "/groups")
    public WebElement linkToGroups;


    //new group elements
    @FindBy(id = "group_name")
    public WebElement newGroupNameField;


    @Override
    public void open() {
        driver.get(HOSTNAME+"/groups");
    }
}
