package ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element extends CustomElement{

    public Element (WebElement element, WebDriver driver) {
        super(element, driver);
    }

    public Element (By locator, WebDriver driver) {
        super(locator, driver);
    }
}
