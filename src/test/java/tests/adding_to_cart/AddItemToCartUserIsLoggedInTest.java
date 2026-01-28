package tests.adding_to_cart;

import data.User;
import helpers.TestUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import ui.components.popups.AccountPopup;
import ui.pages.CartPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddItemToCartUserIsLoggedInTest extends BaseTest {

    private final static String EXPECTED_TEXT_FROM_SEARCH = "Поиск по миллионам товаров";
    private final static String ITEM_NAME = "Воздушный фильтр Filtron AK362/1";

    private User defaultUser = User.getDefaultUser();

    @Test
    public void test() {
        //1. Login as test user
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        //User is successfully logged in. Search field with text: 'Поиск по миллионам товаров' is visible
        assertEquals(mainPage.getHeaderComponent().getTextFromSearch(), EXPECTED_TEXT_FROM_SEARCH, "Verify text into search field");

        //2. Input in search field item name
        // (e.g: Воздушный фильтр Filtron AK362/1)
        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME);

        //Item link appears below the search
        assertTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME),
                "Verify visibility of item: " + ITEM_NAME);

        //3. Open item page
        ItemPage itemPage = mainPage.getHeaderComponent().openItemPage(ITEM_NAME);

        //Page with searched item is opened. The price is visible
        double price = itemPage.getPrice();

        assertTrue(itemPage.isPriceVisible(), "Verify price visibility on item page");

        assertEquals(itemPage.getItemName(), ITEM_NAME, "Verify item name");

        //4. Add item to cart and then open user's cart page
        itemPage.addItemToCart();

        CartPage cartPage = itemPage.openCartPageWithAddedItem();

        //Cart page is opened with the one item. The price is the same (step 3)
        TestUtils.sleep(3000);

        assertEquals(cartPage.getCountOfItemsAddedToCart(), 1, "Verify count of added items");

        assertEquals(cartPage.getItemPrice(ITEM_NAME), price, "Verify price on cart page");

        //5. Logout from test user account
        AccountPopup accountPopup = cartPage.getHeaderComponent().openAccountPopup();
        accountPopup.assertVisible();

        accountPopup.logout();

        cartPage.returnToMainPage();

        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 0,
                "Verify if user is logged out");
        //User was logged out
        //6. Log in as test user and go to the user's cart page
        mainPage.loginAsUser(defaultUser);

        mainPage.openCartPage();

        //The only one item is visible. The price is the same like in step 3 and step 4
        assertEquals(cartPage.getCountOfItemsAddedToCart(), 1, "Verify count of added items");

        assertEquals(cartPage.getItemPrice(ITEM_NAME), price, "Verify price on cart page");


        System.out.println("Test is passed");
    }

    @AfterMethod
    public void cleanUp() {
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        CartPage cartPage = mainPage.openCartPage();

        if (cartPage.isItemVisible(ITEM_NAME)) {
            cartPage.removeItem(ITEM_NAME);
        }
    }
}
