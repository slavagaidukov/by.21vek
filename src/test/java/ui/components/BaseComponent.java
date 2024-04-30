package ui.components;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import ui.elements.Element;
import ui.elements.ElementsDecorator;
import ui.pages.BasePage;

import static org.testng.Assert.assertTrue;


@Data
public abstract class BaseComponent {

    public final static int DIALOG_TIMEOUT = 10;

    private BasePage page;
    private WebDriver driver;
    private String logicalName;
    private By locator;
    private Element componentElement;

    protected BaseComponent(String name, BasePage page, By locator) {
        this.logicalName = name;
        this.page = page;
        this.driver = page.getDriver();
        ElementLocatorFactory factory = new DefaultElementLocatorFactory(driver);
        ElementsDecorator decorator = new ElementsDecorator(factory, driver);
        PageFactory.initElements(decorator, this);
        this.componentElement = new Element(locator, page.getDriver());
    }

    public void assertVisible() {
        assertTrue(componentElement.isVisible(DIALOG_TIMEOUT), getLogicalName() + " is visible");
        getPage().getLogger().info(getLogicalName() + " is visible");
    }
}
