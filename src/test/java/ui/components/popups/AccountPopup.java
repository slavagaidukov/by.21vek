package ui.components.popups;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.BaseComponent;
import ui.elements.Button;
import ui.elements.Element;
import ui.pages.BasePage;
import ui.pages.FavoriteItemsPage;

public class AccountPopup extends BaseComponent {

    @FindBy(xpath = ".//button[@data-testid='loginButton']")
    private Button loginButton;

    @FindBy(xpath = ".//div[text()='Выход']")
    private Button logoutButton;

    @FindBy(xpath = ".//div[@data-testid='profile-favorite-counter']/span")
    private Element favoriteItemsCount;

    @FindBy(xpath = ".//div[text()='Избранные товары']")
    private Button favoriteItemsButton;

    public AccountPopup(BasePage page) {
        super("Account popup", page, By.xpath(".//div[@data-testid='userToolsDropDown']"));
    }

    public FavoriteItemsPage openFavoriteItemsPage() {
        getPage().getLogger().info("Open favorite items page");
        favoriteItemsButton.click();
        FavoriteItemsPage favoriteItemsPage = new FavoriteItemsPage(getDriver());
        favoriteItemsPage.assertIsOpened();
        return favoriteItemsPage;
    }

    public LoginPopup openLoginPopup() {
        getPage().getLogger().info("Open login popup");
        loginButton.click();
        LoginPopup loginPopup = new LoginPopup(getPage());
        loginPopup.assertVisible();
        return loginPopup;
    }

    public void logout() {
        getPage().getLogger().info("Logout from account");
        logoutButton.click();
        this.getComponentElement().waitForInvisibility();
    }

    public boolean userIsNotLoggedIn() {
        return loginButton.isVisible(0);
    }

    public int getFavoriteItemsCount() {
        return Integer.parseInt(favoriteItemsCount.getText());
    }

    public boolean isFavoriteItemsCountElementVisible() {
        return favoriteItemsCount.isVisible();
    }
}
