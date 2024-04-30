package ui.components.favorite_items_page_component;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.BaseComponent;
import ui.elements.Button;
import ui.elements.Element;
import ui.pages.BasePage;

public class FavoriteItemsPageItemComponent extends BaseComponent {

    @FindBy(xpath = ".//div[contains(@class,'OldProductCard_price')]")
    private Element priceElement;

    @FindBy(xpath = ".//button[text()='Удалить из списка']")
    private Button removeItemButton;

    public FavoriteItemsPageItemComponent(BasePage page, String item) {
        super("Favorite items page item " + item + " component", page,
                By.xpath(".//div[contains(@class,'OldProductCard_card__')][.//a[text()='" + item + "']]"));
    }

    public double getPrice() {
        return Double.parseDouble(priceElement.getText().
                replace(",", ".").replace("р.", "").trim());
    }

    public boolean isRemoveButtonVisible() {
        return removeItemButton.isVisible();
    }

    public void remove() {
        removeItemButton.click();
        removeItemButton.waitForInvisibility();
    }
}
