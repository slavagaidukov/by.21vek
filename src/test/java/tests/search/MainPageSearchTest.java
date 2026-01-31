package tests.search;

import org.testng.annotations.Test;
import tests.BaseTest;
import ui.pages.MainPage;

import static listeners.AllureAssert.*;

public class MainPageSearchTest extends BaseTest {
    private final static String ITEM_NAME_1 = "Топливный фильтр Knecht/Mahle KL572";
    private final static String ITEM_NAME_2 = "Салонный фильтр Filtron K1160A-2X (угольный, 2шт)";

    private final static String EXPECTED_CATEGORY = "Фильтры топливные Knecht/Mahle";

    @Test(description = "Verify search on main page")
    public void test() {
        //1. Open main page and search by item
        //(e.x.: Топливный фильтр Knecht/Mahle KL169/4D)
        MainPage mainPage = openApp();

        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME_1);

        //Item is visible. Category name is "Фильтры топливные"
        verifyTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_1),
                "Verify visibility of item: " + ITEM_NAME_1);

        verifyEquals(mainPage.getHeaderComponent().getCategoryOfItemAfterSearch(), EXPECTED_CATEGORY,
        "Verify category after search");

        //2. Clear search using Ctrl+A and Delete
        mainPage.getHeaderComponent().clearSearchUsingControlAndDelete();

        //Item is not visible
        verifyTrue(!mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_1),
                "Verify visibility of item: " + ITEM_NAME_1 + " after clearing of search using Control + Delete");

        //3. Search by item
        mainPage.getHeaderComponent().searchByInfo(ITEM_NAME_2);

        //Item is visible
        verifyTrue(mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_2),
                "Verify visibility of item: " + ITEM_NAME_2);

        //4. Clear search using clear button
        mainPage.getHeaderComponent().clearSearchUsingButton();

        //Item is not visible
        verifyTrue(!mainPage.getHeaderComponent().isItemVisibleAfterSearch(ITEM_NAME_2),
                "Verify visibility of item: " + ITEM_NAME_2 + " after clearing of search using clear search button");
    }
}
