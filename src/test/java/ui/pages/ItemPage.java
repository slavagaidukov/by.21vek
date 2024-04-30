package ui.pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.HeaderComponent;
import ui.elements.Button;
import ui.elements.Element;

@Data
public class ItemPage extends BaseMainPage{

    @FindBy(id = "favoriteOldProductCard")
    private Button favouriteItemsButton;

    @FindBy(xpath = ".//span[text()='Удалить из избранного']")
    private Button removeFromFavoritesButton;

    @FindBy(xpath = ".//button[text()='В корзину']")
    private Button addItemToCartButton;

    @FindBy(xpath = ".//a[text()='В корзине']")
    private Button itemInCartButton;

    @FindBy(css = "span.item__price span")
    private Element priceElement;

    @FindBy(css = "div.content__header h1")
    private Element itemNameElement;

    public ItemPage(WebDriver driver) {
        super(driver, "Item page");
    }

    public boolean isOpened() {
        return favouriteItemsButton.isVisibleWithPageTimeout();
    }

    public double getPrice() {
        return Double.parseDouble(priceElement.waitElement().getAttribute("data-price"));
    }

    public boolean isPriceVisible() {
        return priceElement.isVisible(0);
    }

    public boolean isRemoveFromFavoritesButtonVisible() {
        return removeFromFavoritesButton.isVisible(0);
    }

    public String getItemName() {
        return itemNameElement.getText();
    }

    public void addItemToCart() {
        getLogger().info("Add item to cart");
        addItemToCartButton.click();
        addItemToCartButton.waitForInvisibility();
    }

    public CartPage openCartPageWithAddedItem() {
        getLogger().info("Open cart page with added time");
        itemInCartButton.click();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.assertIsOpened();
        return cartPage;
    }

    public void addItemToFavorites() {
        getLogger().info("Add item to favorites");
        favouriteItemsButton.click();
        removeFromFavoritesButton.waitElement();
    }

    public boolean isPossibleToAddItemToFavorites() {
        return favouriteItemsButton.isVisible() && favouriteItemsButton.getText().equals("Добавить в избранное");
    }

}
