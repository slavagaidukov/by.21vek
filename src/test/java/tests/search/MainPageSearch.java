package tests.search;

import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.MainPage;

import static org.testng.Assert.*;

public class MainPageSearch extends BaseTest {
    private final static String ITEM_NAME_1 = "Топливный фильтр Knecht/Mahle KL169/4D";
    private final static String ITEM_NAME_2 = "Салонный фильтр Filtron K1160A-2X (угольный, 2шт)";

    private final static String EXPECTED_CATEGORY = "Фильтры топливные";
    @Test
    public void test() {
        //1. Open main page and search by item
        //(e.x.: Топливный фильтр Knecht/Mahle KL169/4D)
        MainPage mainPage = openApp();

        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME_1);

        //Item is visible. Category name is "Фильтры топливные"
        assertTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_1),
                "Verify visibility of item: " + ITEM_NAME_1);

        assertEquals(mainPage.getHeaderComponent().getCategoryOfItemAfterSearch(), EXPECTED_CATEGORY,
        "Verify category after search");

        //2. Clear search using Ctrl+A and Delete
        mainPage.getHeaderComponent().clearSearchUsingControlAndDelete();

        //Item is not visible
        assertFalse(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_1),
                "Verify visibility of item: " + ITEM_NAME_1 + " after clearing of search using Control + Delete");

        //3. Search by item
        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME_2);

        //Item is visible
        assertTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_2),
                "Verify visibility of item: " + ITEM_NAME_2);

        //4. Clear search using clear button
        mainPage.getHeaderComponent().clearSearchUsingButton();

        //Item is not visible
        assertFalse(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_2),
                "Verify visibility of item: " + ITEM_NAME_2 + " after clearing of search using clear search button");


        System.out.println("Test passed");
    }
}
