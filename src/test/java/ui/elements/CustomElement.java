package ui.elements;

import helpers.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomElement {

    private final static int ELEMENT_TIMEOUT = 5;
    private final static int PAGE_LOAD_TIMEOUT = 10;

    protected WebElement webElement;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public CustomElement(WebElement webElement, WebDriver driver) {
        this.webElement = webElement;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ELEMENT_TIMEOUT));
    }

    public CustomElement(By locator, WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ELEMENT_TIMEOUT));
        try {
            this.webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
        }
    }

    public WebElement waitElement() {
        try {
           return wait.until(ExpectedConditions.visibilityOf(webElement));
        }
        catch (StaleElementReferenceException e) {
           return wait.until(ExpectedConditions.visibilityOf(webElement));
        }
    }

    public boolean waitPropertyContains(String propertyName, String value, int timeout) {
        final long start = new Date().getTime();
        do {
            try {
                String actValue = webElement.getAttribute(propertyName);
                if (actValue != null && actValue.contains(value)) return true;
            } catch (Throwable ignore) {
                TestUtils.sleep(100);
            }
        } while (new Date().getTime() - start < timeout * 1000);

        return false;
    }

    public void click() {
        waitElement().click();
    }

    public String getText() {
        waitElement();
        return webElement.getText();
    }

    public void waitForInvisibility() {
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void waitForVisibility() {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public boolean isVisible(int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).
                    until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (WebDriverException|NullPointerException e) {
            return false;
        }
    }

    public boolean notVisible(int timeout) {
        boolean result = true;
        LocalDateTime refreshStartMaximalTime = LocalDateTime.now().plusSeconds(timeout);
        do {
            try {
                result = webElement.isDisplayed();
                if (!result) {
                    break;
                }
            } catch (WebDriverException exception) {

            }
        } while (LocalDateTime.now().isBefore(refreshStartMaximalTime));
        return result;
    }

    public boolean isVisible() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(ELEMENT_TIMEOUT)).
                    until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    public boolean isVisibleWithPageTimeout() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT)).
                    until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    public boolean waitProperty(String propertyName, String value) {
        {
            final long start = new Date().getTime();
            do {
                try {
                    WebElement webElement = this.waitElement();
                    String actValue = webElement.getAttribute(propertyName);
                    if (actValue != null && actValue.equalsIgnoreCase(value)) return true;
                } catch (Throwable ignore) {
                    TestUtils.sleep(100);
                }
            } while (new Date().getTime() - start < ELEMENT_TIMEOUT * 1000);

            return false;
        }
    }

    public String getProperty(String propertyName) {
        return webElement.getAttribute(propertyName);
    }

    public static int getElementTimeout() {
        return ELEMENT_TIMEOUT;
    }
}
