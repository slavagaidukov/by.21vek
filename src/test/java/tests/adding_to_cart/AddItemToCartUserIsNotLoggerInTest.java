package tests.adding_to_cart;

import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.CartPage;
import ui.pages.ItemPage;
import ui.pages.MainPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddItemToCartUserIsNotLoggerInTest extends BaseTest {

    private final static String ITEM_NAME = "Воздушный фильтр Filtron AK362/1";

    @Test
    public void test() {
        //1. Open main page and search by item
        //(e.x.: Воздушный фильтр Filtron AK362/1)
        MainPage mainPage = openApp();

        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME);

        //Item link appears below the search
        assertTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME),
                "Verify visibility of item: " + ITEM_NAME);

        //2. Open item page
        ItemPage itemPage = mainPage.getHeaderComponent().openItemPage(ITEM_NAME);

        //Page with searched item is opened. The price is visible
        double price = itemPage.getPrice();

        assertTrue(itemPage.isPriceVisible(), "Verify price visibility on item page");

        assertEquals(itemPage.getItemName(), ITEM_NAME, "Verify item name");

        //3. Add item to cart and then open cart page
        itemPage.addItemToCart();

        CartPage cartPage = itemPage.openCartPageWithAddedItem();

        //Cart page is opened with the one item. The price is the same (step 3)
        assertEquals(cartPage.getCountOfItemsAddedToCart(), 1, "Verify count of added items");

        assertEquals(cartPage.getItemPrice(ITEM_NAME), price, "Verify price on cart page");

        //4. Go to main page then return to cart page
        mainPage = cartPage.returnToMainPage();

        mainPage.openCartPage();

        //Cart page is opened with the one item. The price is the same (step 3)
        assertEquals(cartPage.getCountOfItemsAddedToCart(), 1, "Verify count of added items");

        assertEquals(cartPage.getItemPrice(ITEM_NAME), price, "Verify price on cart page");


        System.out.println("Test passed");
    }
}
