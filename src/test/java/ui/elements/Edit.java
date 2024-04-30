package ui.elements;

import helpers.TestUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Edit extends CustomElement{

    public Edit (WebElement element, WebDriver driver) {
        super(element, driver);
    }

    public void set(String value) {
        this.waitElement().sendKeys(value);
    }

    public void clearUsingKeys() {
        try {
            WebElement webElement = this.waitElement();
            webElement.sendKeys(Keys.CONTROL + "a");
            webElement.sendKeys(Keys.DELETE);
            TestUtils.sleep(100);
        } catch (Throwable e) {
        }
    }

    public void clear() {
        try {
            WebElement element = this.waitElement();
            element.clear();
        } catch (Throwable e) {
        }
    }
}
