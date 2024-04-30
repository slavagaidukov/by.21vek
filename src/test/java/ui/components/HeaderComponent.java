package ui.components;

import helpers.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.popups.AccountPopup;
import ui.elements.Button;
import ui.elements.CustomElement;
import ui.elements.Edit;
import ui.elements.Element;
import ui.pages.BasePage;
import ui.pages.ItemPage;

public class HeaderComponent extends BaseComponent {

    private final static int SEARCH_TIMEOUT = 5;

    @FindBy(id = "catalogSearch")
    private Edit searchEdit;

    @FindBy(xpath = ".//button[contains(@class,'Search_clearBtn__')]")
    private Button clearSearchButton;

    @FindBy(css = "span.headerCartCount")
    private Element itemsCountInCartElement;

    @FindBy(css = "span.userToolsText")
    private Button accountButton;

    public HeaderComponent(BasePage page) {
        super("Header component", page, By.id("header"));
    }

    public ItemPage searchItemAndOpenItsPage(String item) {
        searchByInfo(item);
        return openItemPage(item);
    }

    public ItemPage openItemPage(String item) {
        getPage().getLogger().info("Open item page");
        createItemLink(item).click();
        ItemPage itemPage = new ItemPage(getDriver());
        itemPage.assertIsOpened();
        return itemPage;
    }

    public AccountPopup openAccountPopup() {
        getPage().getLogger().info("Open account popup");
        accountButton.click();
        AccountPopup accountPopup = new AccountPopup(getPage());
        accountPopup.assertVisible();
        return accountPopup;
    }

    public void searchByInfo(String info) {
        getPage().getLogger().info("Search by info: " + info);
        searchEdit.set(info);
        searchEdit.click();
    }

    public void clearSearchUsingControlAndDelete() {
        searchEdit.clearUsingKeys();
        searchEdit.waitProperty("value", "");
        clearSearchButton.waitForInvisibility();
    }

    public void clearSearchUsingButton() {
        clearSearchButton.click();
        searchEdit.waitProperty("value", "");
        clearSearchButton.waitForInvisibility();
    }

    public boolean isItemVisibleAfterSearch(String item) {
        return createItemLink(item).isVisible(SEARCH_TIMEOUT);
    }

    private Element createItemLink(String item) {
        return new Element(By.xpath("//div[contains(@class,'ProductItem_')]//mark[text()='" + item + "']"), getDriver());
    }

    public String getCategoryOfItemAfterSearch() {
        Element categoryElement = new Element(By.xpath(".//li[.//div[normalize-space(text())='Категории']]//span"), getDriver());
        return categoryElement.waitElement().getText();
    }

    public String getTextFromSearch() {
        return searchEdit.getProperty("placeholder");
    }

    public int getCountOfItemsInCartFromButton() {
        TestUtils.sleep(5000);
        if (!itemsCountInCartElement.isVisible(0)) {
            return 0;
        }
        return Integer.parseInt(itemsCountInCartElement.getText());
    }
}
