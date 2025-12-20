package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.favorite_items_page_component.FavoriteItemsPageItemComponent;
import ui.elements.Element;

public class FavoriteItemsPage extends BaseMainPage {

    @FindBy(xpath = ".//h1[contains(@class,'Title-module__h1') and text()='Избранные товары']")
    private Element pageLabel;

    @FindBy(xpath = ".//p[contains(text(),'У вас пока нет ни одного товара в избранном.')]")
    private Element noItemsLabel;

    @FindBy(xpath = ".//button[@data-testid='Добавлено в избранное']")
    private Element removeItemFromFavorites;

    public FavoriteItemsPage(WebDriver driver) {
        super(driver, "Favorite items page");
    }

    public boolean isOpened() {
        return pageLabel.isVisibleWithPageTimeout();
    }

    public boolean itemExists(String item) {
        return createItemComponent(item).getComponentElement().isVisible(0);
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

    public boolean isPossibleToRemoveItemFromFavorites(String item) {
        return createItemComponent(item).isRemoveButtonVisible();
    }
}
