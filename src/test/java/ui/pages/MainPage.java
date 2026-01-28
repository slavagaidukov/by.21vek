package ui.pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ui.components.popups.AccountPopup;
import ui.components.HeaderComponent;
import ui.elements.Button;


@Data
public class MainPage extends BaseMainPage {

    @FindBy(xpath = "//div[contains(@class,'AgreementCookie_buttons')]//button[./div[text()='Принять']]")
    private Button acceptCookiesButton;

    @FindBy(xpath = "//div[@class='headerCart']")
    private Button cartButton;

    public MainPage(WebDriver driver) {
        super(driver, "Main page");
    }

    public boolean isOpened() {
        return cartButton.isVisibleWithPageTimeout();
    }

    public CartPage openCartPage() {
        logger.info("Open cart page");
        cartButton.click();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.assertIsOpened();
        return cartPage;
    }

    public boolean isAcceptCookiesButtonVisible() {
        return acceptCookiesButton.isVisible(1);
    }

    public void acceptCookies() {
        acceptCookiesButton.click();
        acceptCookiesButton.waitForInvisibility();
    }
}
