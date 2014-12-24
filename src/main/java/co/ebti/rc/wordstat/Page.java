package co.ebti.rc.wordstat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;


public abstract class Page {

    protected WebDriver driver;
    public String HOSTNAME = Hostname.getHostName();

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void type(WebElement webElement, String text) {
        webElement.click();
        webElement.clear();
        webElement.sendKeys(text);
    }


    public abstract void open();

    public boolean isElementPresent(WebElement element) {
        Long startTime = new Date().getTime();
        while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > ((new Date().getTime()) - startTime)) {
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public boolean isElementNotPresent(WebElement element) {
        Long startTime = new Date().getTime();
        while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > ((new Date().getTime()) - startTime)) {
            try {
                if (element.isDisplayed()) {
                    return false;
                }
            } catch (Exception ignored) {
                return true;
            }
        }
        return true;
    }

    /**
     * Web element is checked for displaying, no more than specified timeout
     * @param element web element to expect
     * @param timeout in seconds
     * @return
     */
    public boolean isElementPresent(WebElement element, Long timeout) {
        Long startTime = new Date().getTime();
        while (timeout * 1000 > ((new Date().getTime()) - startTime)) {
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void waitForElementVisible10Sec (WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isElementEnabled(WebElement element) {
        Long startTime = new Date().getTime();
        while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > ((new Date().getTime()) - startTime)) {
            try {
                if (element.isEnabled()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }
    public boolean isElementNotEnabled(WebElement element) {
        Long startTime = new Date().getTime();
        while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > ((new Date().getTime()) - startTime)) {
            try {
                if (element.isEnabled()) {
                    return false;
                }
            } catch (Exception ignored) {
            }
        }
        return true;
    }

    public boolean isPageLoaded(String url) {
        Long startTime = new Date().getTime();
        while (Long.valueOf(ConfigProperties.getProperty("page.wait")) * 1000 > ((new Date().getTime()) - startTime)) {

            try {
                String updatedUrl = url.replace("\"", "%22").replace(" ", "%20");
                if (driver.getCurrentUrl().contains(url) || driver.getCurrentUrl().contains(updatedUrl)) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public boolean isAnyTextPresentInElement(WebElement element) {
        if (isElementPresent(element)) {
            Long startTime = new Date().getTime();
            while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > (new Date().getTime()) - startTime) {
                String elementText = element.getText();
                if (elementText.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTextPresentInElement(WebElement element, String text) {
        if (isElementPresent(element)) {
            Long startTime = new Date().getTime();
            while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 2000 > (new Date().getTime()) - startTime) {
                try {
                    String elementText = element.getText();
                    if (elementText.equals(text)) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    public boolean waitForTextPresentInElement(WebElement element, String text) {
        if (isElementPresent(element)) {
            Long startTime = new Date().getTime();
            while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > (new Date().getTime()) - startTime) {
                try {
                    String elementText = element.getText();
                    if (elementText.equals(text)) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    public boolean isTextNotPresentInElement(WebElement element, String text) {
        if (isElementPresent(element)) {
            Long startTime = new Date().getTime();
            while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > (new Date().getTime()) - startTime) {
                String elementText = element.getText();
                if (!elementText.contains(text)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAnyValueAttributePresentInElement(WebElement element) {
        if (isElementPresent(element)) {
            Long startTime = new Date().getTime();
            while (Long.valueOf(ConfigProperties.getProperty("element.wait")) * 1000 > (new Date().getTime()) - startTime) {
                String attributeValue = element.getAttribute("value");
                if (attributeValue.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean textOnThePageContains(String text){
        return driver.getPageSource().contains(text);
    }

    public void goToTheLinkWhichContainText(String testCategoryName){
        driver.findElement(By.linkText(testCategoryName)).isDisplayed();
        driver.findElement(By.linkText(testCategoryName)).click();
    }

    public WebDriver getDriver() {
        return driver;
    }

}
