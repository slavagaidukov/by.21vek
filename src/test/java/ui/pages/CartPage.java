package ui.pages;

import helpers.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.cart_page_components.CartPageItemComponent;
import ui.elements.Element;

public class CartPage extends BaseMainPage {

    @FindBy(xpath = ".//div[@class='Tabs-module__inner'][.//span[text()='Корзина']]")
    private Element cartLabel;

    @FindBy(xpath = ".//button[normalize-space(text())='Закрыть']")
    private Element closeUnicefNotificationButton;

    public CartPage(WebDriver driver) {
        super(driver, "Cart page");
    }

    public boolean isOpened() {
        if (closeUnicefNotificationButton.isVisible()) {
            closeUnicefNotificationButton.click();
            closeUnicefNotificationButton.waitForInvisibility();
        }
        return cartLabel.isVisibleWithPageTimeout();
    }

    public int getCountOfItemsAddedToCart() {
        TestUtils.sleep(2000);
        return getDriver().findElements(By.xpath(".//div[@data-testid='basket-item']")).size();
    }

    private CartPageItemComponent createCartPageItemComponent(String item) {
        return new CartPageItemComponent(this, item);
    }

    public double getItemPrice(String item) {
        return createCartPageItemComponent(item).getPrice();
    }

    public void removeItem(String item) {
        createCartPageItemComponent(item).remove();
    }

    public boolean isItemVisible(String item) {
        return createCartPageItemComponent(item).getComponentElement().isVisible(0);
    }
}
