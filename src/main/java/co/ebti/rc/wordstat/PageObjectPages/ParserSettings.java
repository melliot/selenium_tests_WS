package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Korniev.Oleksandr on 29.10.2014.
 */
public class ParserSettings extends Page {
    public ParserSettings(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "seo_settings_parser_backend")
    public WebElement seoSettingsParserBackend;

    @FindBy(name = "commit")
    public WebElement saveButton;

    @FindBy(linkText =  "cancel")
    public WebElement cancelButton;

    @Override
    public void open() {
        driver.get(HOSTNAME+"/seo_settings");
    }
}
