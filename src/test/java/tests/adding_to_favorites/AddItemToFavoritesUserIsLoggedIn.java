package tests.adding_to_favorites;

import data.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import ui.components.popups.AccountPopup;
import ui.pages.FavoriteItemsPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.*;

public class AddItemToFavoritesUserIsLoggedIn extends BaseTest {

    private final static String ITEM = "Воздушный фильтр Filtron AK362/1";

    private User defaultUser = User.getDefaultUser();
    @Test
    public void test() {
        //1. Open main page and login as user
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        //Main page is opened. User is logged in
        //2. Open item page (e.x.: Топливный фильтр Knecht/Mahle KL169/4D)
        ItemPage itemPage = mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM);

        //Item page is opened. Price is displayed
        double price = itemPage.getPrice();

        assertTrue(itemPage.isPriceVisible(), "Verify price visibility on item page");

        //3. Add to favorites
        itemPage.addItemToFavorites();

        //Item is added to favorites. Star icon become red
        //It is possible to remove item from favorites
        assertTrue(itemPage.isPossibleToRemoveItemFromFavorites(),
                "Verify if it is possible to remove item from favorites");

        //4. Open account popup
        AccountPopup accountPopup = itemPage.getHeaderComponent().openAccountPopup();;

        //Account popup is opened. Near the tab "Избранные товары" '1' digit is visible
        assertEquals(accountPopup.getFavoriteItemsCount(), 1, "Verify count of favorite items");

        //5. Open favorites page
        FavoriteItemsPage favoriteItemsPage = accountPopup.openFavoriteItemsPage();

        //Favorites page is opened. Price is visible and the same as at item page. It is possible to remove item from favorites
        assertTrue(favoriteItemsPage.itemExists(ITEM), "Verify item visibility");
        assertEquals(favoriteItemsPage.getItemPrice(ITEM), price,
                "Verify item price on Favorite Items page");
        assertTrue(favoriteItemsPage.isPossibleToRemoveItemFromFavorites(ITEM),
                "Verify if it is possible to remove item from favorites");

        //6. Remove item from favorites
        favoriteItemsPage.removeItem(ITEM);

        //Favorites list is empty
        favoriteItemsPage.returnToMainPage();

        mainPage.getHeaderComponent().openAccountPopup().openFavoriteItemsPage();

        //Favorites list is empty
        assertTrue(favoriteItemsPage.isNoItemsLabelVisible(), "Verify if items are not visible");
        assertFalse(favoriteItemsPage.itemExists(ITEM), "Verify if favorites list is empty");

        //7. Open accounts popup
        favoriteItemsPage.getHeaderComponent().openAccountPopup();

        //Near 'Избранные товары' tab is no digits
        assertTrue(favoriteItemsPage.isNoItemsLabelVisible(), "Verify if no items label is visible");

        //8. Open cart page
        favoriteItemsPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM);

        //Is is possible to add item to favorites
        Assert.assertTrue(itemPage.isPossibleToAddItemToFavorites(), "Verify possibility to add items to favorites");


        System.out.println("Test passed");
    }

    @AfterMethod
    public void cleanUp() {
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        AccountPopup accountPopup = mainPage.getHeaderComponent().openAccountPopup();

        FavoriteItemsPage favoriteItemsPage = accountPopup.openFavoriteItemsPage();

        if (favoriteItemsPage.itemExists(ITEM)) {
            favoriteItemsPage.removeItem(ITEM);
        }
    }
}
