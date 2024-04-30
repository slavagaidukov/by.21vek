package ui.pages;

import data.User;
import helpers.TestUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.HeaderComponent;
import ui.components.popups.AccountPopup;
import ui.components.popups.LoginPopup;
import ui.elements.Button;
import ui.elements.Element;

@Data
@NoArgsConstructor
public abstract class BaseMainPage extends BasePage {

    @FindBy(css = "div.logotype")
    private Element logotype;

    @FindBy(css = "span.userToolsText")
    protected Button accountButton;

    private HeaderComponent headerComponent;

    public BaseMainPage(WebDriver driver, String pageName) {
        super(driver, pageName);
        headerComponent = new HeaderComponent(this);
    }

    public MainPage returnToMainPage() {
        getLogger().info("Return to main page");
        logotype.waitElement().click();
        MainPage mainPage = new MainPage(getDriver());
        mainPage.assertIsOpened();
        return mainPage;
    }

    public void loginAsUser(User user) {
        getLogger().info("Login as user: " + user.getEmail());
        headerComponent.openAccountPopup();
        AccountPopup accountPopup = new AccountPopup(this);
        accountPopup.assertVisible();
        if (accountPopup.userIsNotLoggedIn()) {
            LoginPopup loginPopup = accountPopup.openLoginPopup();
            loginPopup.doLogin(user);
        } else {
            getLogger().info("User is already logged");
            accountButton.click();
            accountPopup.getComponentElement().waitForInvisibility();
        }
    }
}
