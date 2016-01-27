package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPageElements extends Page {

    public MainPageElements(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "sName")
    public WebElement searchField;

    @FindBy(id = "logo")
    public WebElement gsmArenaLogoImage;

    @FindBy(id = "login-active")
    public WebElement loginButton;

    @FindBy(id = "quick-search-button")
    public WebElement searchButton;

    @Override
    public void open() {
        System.out.println(HOSTNAME);
        driver.get(HOSTNAME);
    }

    public void openWithLink(String link) {
        System.out.println(HOSTNAME);
        driver.get(HOSTNAME+link);
    }
}
