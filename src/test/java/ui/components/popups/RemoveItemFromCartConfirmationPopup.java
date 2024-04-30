package ui.components.popups;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.BaseComponent;
import ui.elements.Button;
import ui.pages.BasePage;
import ui.pages.CartPage;

public class RemoveItemFromCartConfirmationPopup extends BaseComponent {

    @FindBy(xpath = ".//button[./div[text()='Удалить']]")
    private Button confirmRemovingButton;

    public RemoveItemFromCartConfirmationPopup(BasePage page) {
        super("Remove item from cart popup", page, By.xpath(".//div[./h5[text()='Удалить товар из корзины']]"));
    }

    public void confirmRemoving() {
        getPage().getLogger().info("Confirm removing");
        confirmRemovingButton.click();
        this.getComponentElement().waitForInvisibility();
    }
}
