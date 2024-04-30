package tests.adding_to_cart;

import data.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.CartPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.*;

public class AddItemToNotEmptyCart extends BaseTest {

    private final static String ITEM_NAME_1 = "Салонный фильтр Filtron K1160A-2X (угольный, 2шт)";
    private final static String ITEM_NAME_2 = "Воздушный фильтр Filtron AK362/1";

    private User defaultUser = User.getDefaultUser();

    @Test
    public void test() {
        //Preconditions: Login as any user and add any item to cart
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        ItemPage itemPage = mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME_1);

        itemPage.addItemToCart();

        //1. Open Main page
        itemPage.returnToMainPage();

        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 1,
                "Verify if item is added");

        //2. Input in search field item name and open item page
        // (e.g: Воздушный фильтр Filtron AK362/1)
        mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME_2);

        //Page with searched item is opened
        //3. Add item to cart and open Main page
        itemPage.addItemToCart();

        itemPage.returnToMainPage();

        //Main page is opened. Cart element with digit '2' is visible
        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 2,
                "Verify if the second item is added");

        //4. Open cart page
        CartPage cartPage = mainPage.openCartPage();

        //Cart page is opened
        //5. Remove any item from cart
        cartPage.removeItem(ITEM_NAME_1);

        //Item removed. The other item is visible
        assertFalse(cartPage.isItemVisible(ITEM_NAME_1), "Verify if item was removed");
        assertTrue(cartPage.isItemVisible(ITEM_NAME_2), "Verify if item was not removed");

        //6. Open Main page
        cartPage.returnToMainPage();

        //Main page is opened. Cart element with digit '1' is visible
        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 1,
                "Verify if count of items after removing");


        System.out.println("Test passed");
    }

    @AfterClass
    public void tearDown() {
        MainPage mainPage = openApp();

        mainPage.loginAsUser(defaultUser);

        CartPage cartPage = mainPage.openCartPage();

        if (cartPage.isItemVisible(ITEM_NAME_1)) {
            cartPage.removeItem(ITEM_NAME_1);
        }

        if (cartPage.isItemVisible(ITEM_NAME_2)) {
            cartPage.removeItem(ITEM_NAME_2);
        }

        closeBrowser();
    }
}
