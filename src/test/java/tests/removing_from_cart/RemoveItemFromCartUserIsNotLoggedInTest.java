package tests.removing_from_cart;

import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.CartPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.*;

public class RemoveItemFromCartUserIsNotLoggedInTest extends BaseTest {

    private final static String ITEM_NAME = "Воздушный фильтр Filtron AK362/1";

    @Test
    public void test() {
        //1. Open Main page and input in search field item name and open item page
        // (e.g: Воздушный фильтр Filtron AK362/1)
        MainPage mainPage = openApp();

        ItemPage itemPage = mainPage.getHeaderComponent().searchItemAndOpenItsPage(ITEM_NAME);

        //Page with searched item is opened. The price is visible
        assertTrue(itemPage.isPriceVisible(), "Verify price visibility on item page");

        //2. Add item to cart and open Main page
        itemPage.addItemToCart();

        itemPage.returnToMainPage();

        //Main page is opened. Cart element with digit '1' is visible
        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 1,
                "Verify if item is added");

        //3. Open cart page
        CartPage cartPage = mainPage.openCartPage();

        //Cart page is opened
        //4. Click remove button then confirm removing
        cartPage.removeItem(ITEM_NAME);

        //Item was removed from cart
        assertFalse(cartPage.isItemVisible(ITEM_NAME), "Verify if item was removed");

        //5. Open Main page
        cartPage.returnToMainPage();

        //
        //Main page is opened. Cart element without digit '1' is visible
        assertEquals(mainPage.getHeaderComponent().getCountOfItemsInCartFromButton(), 0,
                "Verify if item is added");


        System.out.println("Test passed");
    }
}
