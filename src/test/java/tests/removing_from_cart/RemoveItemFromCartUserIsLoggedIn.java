package tests.removing_from_cart;

import data.User;
import helpers.TestUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.CartPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class RemoveItemFromCartUserIsLoggedIn extends BaseTest {

    private final static String ITEM_NAME = "Воздушный фильтр Filtron AK362/1";

    private User defaultUser = User.getDefaultUser();

    @Test
    public void test() {
        //1. Open Main page and log in as any user
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        //Main page is opened. User is logged in
        //2. Input in search field item name and open item page
        // (e.g: Воздушный фильтр Filtron AK362/1)
        ItemPage itemPage = mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME);

        //Page with searched item is opened
        assertEquals(itemPage.getItemName(), ITEM_NAME, "Verify item name");

        //3. Add item to cart and open Main page
        itemPage.addItemToCart();

        itemPage.returnToMainPage();

        //Main page is opened. Cart element with digit '1' is visible
        TestUtils.sleep(3000);

        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 1,
                "Verify if item is added");

        //4. Open cart page
        CartPage cartPage = mainPage.openCartPage();

        //Cart page is opened
        //5. Remove item from cart
        cartPage.removeItem(ITEM_NAME);

        //Item removed. Text 'Ваша корзина пуста' is visible
        assertFalse(cartPage.isItemVisible(ITEM_NAME), "Verify if item was removed");

        //6. Open Main page
        cartPage.returnToMainPage();

        //Main page is opened. Cart element without digit '1' is visible

        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 0,
                "Verify if item is added");


        System.out.println("Test passed");
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
