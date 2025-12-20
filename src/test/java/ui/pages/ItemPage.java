package ui.pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.elements.Button;
import ui.elements.CustomElement;
import ui.elements.Element;

@Data
public class ItemPage extends BaseMainPage{

    @FindBy(xpath = ".//button[@aria-label='В избранное']")
    private Button favouriteItemsButton;

    @FindBy(xpath = ".//button[@aria-label='В избранном']")
    private Button removeItemFromFavouritesButton;

    @FindBy(xpath = ".//button[@aria-label='Добавить в корзину']")
    private Button addItemToCartButton;

    @FindBy(xpath = ".//div[text()='В корзине']")
    private Button itemInCartButton;

    @FindBy(xpath = ".//span[contains(@class,'ProductPrice')]")
    private Element priceElement;

    @FindBy(css = "div[id='content'] h1")
    private Element itemNameElement;

    public ItemPage(WebDriver driver) {
        super(driver, "Item page");
    }

    public boolean isOpened() {
        return favouriteItemsButton.isVisibleWithPageTimeout();
    }

    public double getPrice() {
        return Double.parseDouble(priceElement.getText().replaceAll("\\s(.*)", "").replace(",", "."));
    }

    public boolean isPriceVisible() {
        return priceElement.isVisible(0);
    }

    public boolean isRemoveFromFavoritesButtonVisible() {
        return  favouriteItemsButton.isVisible() && favouriteItemsButton.getProperty("class").contains("pink-tentiary");
    }

    public String getItemName() {
        return itemNameElement.getText();
    }

    public void addItemToCart() {
        getLogger().info("Add item to cart");
        addItemToCartButton.click();
        itemInCartButton.waitForVisibility();
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
        favouriteItemsButton.waitPropertyContains("class", "pink-tentiary", CustomElement.getElementTimeout());
    }

    public boolean isPossibleToAddItemToFavorites() {
        return favouriteItemsButton.isVisible();
    }

    public boolean isPossibleToRemoveItemFromFavorites() {
        return removeItemFromFavouritesButton.isVisible();
    }
}
