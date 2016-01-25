package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Group extends Page {

    public Group(WebDriver driver) {
        super(driver);
    }

    //Anchor file elements
    @FindBy(xpath = "//a[contains(text(),'Anchor file')]")
    public WebElement anchorFile;

    @FindBy(xpath = "//a[contains(text(),'Reset anchor form')]")
    public WebElement resetAnchorFormButton;

    @FindBy(name = "template_name")
    public WebElement templateNameField;

    @FindBy(name = "load_template")
    public WebElement loadTemplateMenu;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement saveTemplateButton;

    @FindBy(id = "delete_template")
    public WebElement deleteTemplateButton;

    @FindBy(xpath = "//a[contains(text(),'Remove all templates')]")
    public WebElement removeAllTemplatesButton;

    @FindBy(css = "a[title=\"Add preposition\"]")
    public WebElement addPreposition;

    @FindBy(css = "a[title=\"Add domain\"]")
    public WebElement addDomain;

    @FindBy(xpath = "//a[contains(text(),'mix one more group')]")
    public WebElement mixOneMoreGroup;

    @FindBy(css = "a[title=\"Add buzzwords\"] > i.icon-plus")
    public WebElement addBuzzwords;

    @FindBy(css = "button.btn.btn-primary")
    public WebElement downloadButton;

    @FindBy(xpath = "//button[2]")
    public WebElement previewButton;

    @FindBy(id = "search_result")
    public WebElement searchResultField;

    @FindBy(id = "search_result_exclude")
    public WebElement searchResultExcludeField;

    @FindBy(id = "search_result_weight_filter")
    public WebElement searchResultWeightFilterField;

    @FindBy(css = "form.form-search > input[name=\"commit\"]")
    public WebElement findButton;

    @FindBy(id = "search-clear")
    public WebElement clearSearchButton;

    @FindBy(id = "strategy")
    public WebElement resultsPerPageMenu;

    @FindBy(id = "suggests")
    public WebElement toggleAllSuggests;

    @FindBy(xpath = "//a[contains(text(),'Full exact weight')]")
    public WebElement sortByFullExactWeight;

    @FindBy(xpath = "//a[contains(text(),'Exact weight')]")
    public WebElement sortByExactWeight;

    @FindBy(xpath = "//a[contains(text(),'Weight')]")
    public WebElement sortByWeight;

    @FindBy(xpath = "//a[contains(text(),'Collection Type')]")
    public WebElement sortByCollectionType;

    //List elements
    @FindBy(xpath = "//a[contains(text(),'List')]")
    public WebElement list;

    @FindBy(id = "group_blacklist")
    public WebElement blacklistSuggestsField;

/*    @FindBy(id = "task_stop_words")
    public WebElement taskStopWords;*/

    @FindBy(css = "div.controls > input[name=\"commit\"]")
    public WebElement updateBlacklistSuggestsButton;

    //Import/Export elements
    @FindBy(xpath = "//a[contains(text(),'Import/Export')]")
    public WebElement importExport;

    @FindBy(id = "upload_update")
    public WebElement uploadUpdate;

    @FindBy(id = "upload_replace")
    public WebElement uploadReplace;

    @FindBy(css = "div.row-fluid > form > div.span1 > input[name=\"commit\"]")
    public WebElement uploadButtonIE;

    @FindBy(xpath = "//a[contains(text(),'Download')]")
    public WebElement downloadButtonIE;

    //Keyword tree elements
    @FindBy(xpath = "//a[contains(text(),'Keywords tree')]")
    public WebElement keywordsTree;

    @FindBy(xpath = "//a[contains(text(),'Tree')]")
    public WebElement simpleTree;

    @FindBy(xpath = "//a[contains(text(),'Unique Tree')]")
    public WebElement uniqueTree;

    @FindBy(id = "tree_configuration_name")
    public WebElement newPresetName;

    @FindBy(css = "span.btn.btn-link")
    public WebElement addConjunctionsAndPrepositions;

    @FindBy(id = "tree_configuration_root_phrases")
    public WebElement thematicRootPhrases;

    //Stop words page elements
    @FindBy(xpath = "//a[contains(text(),'Stop Words')]")
    public WebElement stopWords;

    @FindBy(xpath = "//a[contains(text(),'Manual Stop Words')]")
    public WebElement manualStopWords;

    @FindBy(id = "stop_word")
    public WebElement mswStopWordField;

    @FindBy(name = "commit")
    public WebElement mswAddStopWord;

    @FindBy(xpath = "//a[contains(text(),'hide all')]")
    public WebElement mswHideAllLocal;

    @FindBy(xpath = "//a[contains(text(),'Rebuild Rating')]")
    public WebElement aswRebuildRating;

    @FindBy(id = "batch_add_to_stop_words")
    public WebElement aswAddToStopWords;

    @FindBy(xpath = "//a[contains(text(),'Words Tree')]")
    public WebElement wordsTree;

    @FindBy(xpath = "//a[contains(text(),'Build Tree')]")
    public WebElement wtBuildTreeButton;

    @FindBy(xpath = "//a[contains(text(),'Help (значения спецсимволов)')]")
    public WebElement treeHelpLink;

    //ASW
    @FindBy(xpath = "//a[contains(text(),'Automatic Stop Words')]")
    public WebElement automaticStopWords;

    @FindBy (id = "regexp_kw")
    public WebElement aswSearchField;

    @FindBy (id = "per_page")
    public WebElement perPageOnASW;


/*    @FindBy(xpath = "//div[@id='js-add-stop-words-holder']/div")
    public WebElement wtAddToStopWords;*/

    @Override
    public void open() {
        driver.get(HOSTNAME+"/groups");
        linkTo_QA_AutoTestInsideGroup_QA.click();
    }

    @FindBy(linkText = "QA_AutoTestInsideGroup_QA")
    public WebElement linkTo_QA_AutoTestInsideGroup_QA;
}
