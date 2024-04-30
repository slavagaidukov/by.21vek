package ui.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ElementsDecorator extends DefaultFieldDecorator {

    private WebDriver driver;

    public ElementsDecorator(ElementLocatorFactory factory, WebDriver driver) {
        super(factory);
        this.driver = driver;
    }

    public Object decorate(ClassLoader loader, Field field) {
        if (isCustomElement(field)) {
            ElementLocator locator = factory.createLocator(field);
            return createElement(loader, locator, field.getType());
        }
        return super.decorate(loader, field);
    }

    private boolean isCustomElement(Field field) {
        return CustomElement.class.isAssignableFrom(field.getType());
    }

    protected <T> T createElement(ClassLoader loader, ElementLocator locator, Class<T> elementClass) {
        WebElement proxy = proxyForLocator(loader, locator);

        try {
            return elementClass.getConstructor(WebElement.class, WebDriver.class).newInstance(proxy, driver);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Web Element can't be represented as " + elementClass);
        }

    }
}
