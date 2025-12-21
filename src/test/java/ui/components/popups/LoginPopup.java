package ui.components.popups;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ui.components.BaseComponent;
import ui.elements.Button;
import ui.elements.Edit;
import ui.pages.BasePage;

public class LoginPopup extends BaseComponent {

    @FindBy(id = "login-email")
    private Edit emailEdit;

    @FindBy(id = "login-password")
    private Edit passwordEdit;

    @FindBy(xpath = ".//button[@data-testid='loginSubmit']")
    private Button loginButton;

    public LoginPopup(BasePage page) {
        super("Login popup", page,
                By.xpath(".//div[@data-testid='modal']"));
    }

    public void doLogin(User user) {
        getPage().getLogger().info("Input user credentials and login");
        setEmail(user);
        setPassword(user);
        loginButton.click();
        this.getComponentElement().waitForInvisibility();
    }

    private void setEmail(User user) {
        emailEdit.set(user.getEmail());
    }

    private void setPassword(User user) {
        passwordEdit.set(user.getPassword());
    }
}
