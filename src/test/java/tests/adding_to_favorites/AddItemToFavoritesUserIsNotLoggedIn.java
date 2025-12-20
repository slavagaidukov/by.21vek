package tests.adding_to_favorites;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import ui.components.popups.AccountPopup;
import ui.pages.FavoriteItemsPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddItemToFavoritesUserIsNotLoggedIn extends BaseTest {

    private final static String ITEM_NAME = "Воздушный фильтр Filtron AP134/10";

    @Test
    public void test() {
        //1. Open main page
        MainPage mainPage = openApp();

        //Main page is opened
        //2. Open item page (e.x.: Топливный фильтр Knecht/Mahle KL169/4D)
        ItemPage itemPage = mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME);

        //Item page is opened. Price is displayed
        double price = itemPage.getPrice();

        assertTrue(itemPage.isPriceVisible(), "Verify price visibility on item page");

        //3. Add to favorites
        itemPage.addItemToFavorites();

        //Item is added to favorites. It is possible to remove item from favorites
        assertTrue(itemPage.isPossibleToRemoveItemFromFavorites(), "Verify if it possible to remove item from favorites");

        //4. Open account popup
        AccountPopup accountPopup = itemPage.getHeaderComponent().openAccountPopup();

        //Account popup is opened. Near the tab "Избранные товары" '1' digit is visible
        assertEquals(accountPopup.getFavoriteItemsCount(), 1, "Verify count of favorite items");

        //5. Open favorites page
        FavoriteItemsPage favoriteItemsPage = accountPopup.openFavoriteItemsPage();

        //Favorites page is opened. Price is visible and the same as at item page.
        //It is possible to remove item from favorites
        assertTrue(favoriteItemsPage.itemExists(ITEM_NAME), "Verify item visibility");
        assertEquals(favoriteItemsPage.getItemPrice(ITEM_NAME), price,
                "Verify item price on Favorite Items page");
        assertTrue(favoriteItemsPage.isPossibleToRemoveItemFromFavorites(ITEM_NAME), "Verify if it possible to remove item from favorites");

        //6. Remove item from favorites
        favoriteItemsPage.removeItem(ITEM_NAME);

        //Favorites list is empty
        favoriteItemsPage.returnToMainPage();

        mainPage.getHeaderComponent().openAccountPopup();
        accountPopup.openFavoriteItemsPage();

        assertTrue(favoriteItemsPage.isNoItemsLabelVisible(), "Verify if no items label is visible");

        //7. Open accounts popup
        favoriteItemsPage.getHeaderComponent().openAccountPopup();

        //Near 'Избранные товары' tab is no digits
        Assert.assertFalse(accountPopup.isFavoriteItemsCountElementVisible(), "Verify favorite items count element visibility");

        //8. Open cart page
        favoriteItemsPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME);

        Assert.assertTrue(itemPage.isPossibleToAddItemToFavorites(), "Verify possibility to add items to favorites");


        System.out.println("Test passed");
    }
}
