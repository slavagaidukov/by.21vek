package ui.components.cart_page_components;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.BaseComponent;
import ui.components.popups.RemoveItemFromCartConfirmationPopup;
import ui.elements.Button;
import ui.elements.Element;
import ui.pages.BasePage;

@Data
public class CartPageItemComponent extends BaseComponent {

    @FindBy(xpath = ".//div[contains(@class,'PriceBlock')]")
    private Element priceElement;

    @FindBy(xpath = ".//button[@aria-label='Удалить товар']")
    private Button removeButton;

    private String item;

    public CartPageItemComponent(BasePage page, String item) {
        super("Cart page item " + item + " component", page,
                By.xpath(".//div[@data-testid='basket-item'][.//a[text()='" + item + "']]"));
        this.item = item;
    }

    public double getPrice() {
        return Double.parseDouble(priceElement.getText().replace(" р.", "").replace(",", "."));
    }

    public void remove() {
        getPage().getLogger().info("Remove item");
        getDriver().findElement(By.xpath(".//div[@data-testid='basket-item'][.//a[text()='" + getItem() + "']]//button[@aria-label='Удалить товар']"))
                .click();
        RemoveItemFromCartConfirmationPopup removeItemFromCartConfirmationPopup =
                new RemoveItemFromCartConfirmationPopup(getPage());
        removeItemFromCartConfirmationPopup.assertVisible();
        removeItemFromCartConfirmationPopup.confirmRemoving();
    }
}
