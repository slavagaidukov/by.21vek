package ui.pages;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.elements.ElementsDecorator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

@Data
@NoArgsConstructor
public abstract class BasePage {
    private final static String SCREENSHOTS_PATH = "C:\\Users\\gaydukovva\\IdeaProjects\\by.21vek\\src\\main\\resources\\screenshots";

    protected Logger logger;

    private final static int WAIT_TIMEOUT_SECONDS = 5;

    protected WebDriver driver;
    protected WebDriverWait wait;
    private String pageName;

    public BasePage (WebDriver driver, String pageName) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        this.pageName = pageName;
        this.logger = LogManager.getLogger();
        ElementLocatorFactory factory = new DefaultElementLocatorFactory(driver);
        ElementsDecorator decorator = new ElementsDecorator(factory, driver);
        PageFactory.initElements(decorator, this);
    }

    public abstract boolean isOpened();

    public void assertIsOpened() {
        assertTrue(isOpened(), pageName + " was opened");
        getLogger().info(pageName + " was opened");
    }

    public void takeScreenshotAndSaveInScreenshotsFolder() {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File file = new File(SCREENSHOTS_PATH + scrFile.getName());
        try {
            Files.copy(scrFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
