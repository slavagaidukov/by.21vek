package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.favorite_items_page_component.FavoriteItemsPageItemComponent;
import ui.elements.Button;
import ui.elements.Element;

public class FavoriteItemsPage extends BaseMainPage {

    @FindBy(xpath = ".//h2[contains(@class,'FavoritesProducts_title') and text()='Избранные товары']")
    private Element pageLabel;

    @FindBy(xpath = ".//div[text()='Нет избранных товаров']")
    private Element noItemsLabel;

    public FavoriteItemsPage(WebDriver driver) {
        super(driver, "Favorite items page");
    }

    public boolean isOpened() {
        return pageLabel.isVisibleWithPageTimeout();
    }

    public boolean itemExists(String item) {
        return createItemComponent(item).getComponentElement().isVisible(0);
    }

    public boolean isPossibleToRemoveItem(String item) {
        return createItemComponent(item).isRemoveButtonVisible();
    }

    public boolean isNoItemsLabelVisible() {
        return noItemsLabel.isVisible();
    }

    public void removeItem(String item) {
        createItemComponent(item).remove();
    }

    public double getItemPrice(String item) {
        return createItemComponent(item).getPrice();
    }

    private FavoriteItemsPageItemComponent createItemComponent(String item) {
        return new FavoriteItemsPageItemComponent(this, item);
    }
}
