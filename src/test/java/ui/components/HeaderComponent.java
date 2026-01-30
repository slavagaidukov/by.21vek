package ui.components;

import helpers.TestUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.popups.AccountPopup;
import ui.elements.Button;
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

    @FindBy(xpath = ".//div[@data-testid='header-count']/span")
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

    @Step("Open item page {item}")
    public ItemPage openItemPage(String item) {
        getPage().getLogger().info("Open item page");
        createItemLink(item).click();
        ItemPage itemPage = new ItemPage(getDriver());
        itemPage.assertIsOpened();
        return itemPage;
    }

    @Step("Open account popup")
    public AccountPopup openAccountPopup() {
        getPage().getLogger().info("Open account popup");
        accountButton.click();
        AccountPopup accountPopup = new AccountPopup(getPage());
        accountPopup.assertVisible();
        return accountPopup;
    }

    @Step("Search by info: {info}")
    public void searchByInfo(String info) {
        getPage().getLogger().info("Search by info: " + info);
        searchEdit.clearUsingKeys();
        searchEdit.set(info);
        searchEdit.click();
    }

    @Step("Clear search using control and delete")
    public void clearSearchUsingControlAndDelete() {
        getPage().getLogger().info("Clear search using control and delete");
        searchEdit.clearUsingKeys();
        searchEdit.waitProperty("value", "");
        clearSearchButton.waitForInvisibility();
    }

    @Step("Clear search using button")
    public void clearSearchUsingButton() {
        getPage().getLogger().info("Clear search using button");
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

    @Attachment
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
