package ui.pages;

import lombok.Getter;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BasePage {
    private static final String SCREENSHOTS_DIR = "target/screenshots";

    @Getter
    protected Logger logger;

    private final static int WAIT_TIMEOUT_SECONDS = 5;

    @Getter
    protected WebDriver driver;
    protected WebDriverWait wait;
    private final String pageName;

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
        if (!isOpened()) {
            takeScreenshot("PageNotOpened_" + pageName);
            throw new AssertionError(pageName + " was not opened");
        }
        logger.info(pageName + " was opened successfully");
    }

    public void takeScreenshot(String screenshotName) {
        try {
            File screenshotsDir = new File(SCREENSHOTS_DIR);
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = screenshotName + "_" + timestamp + ".png";
            File destination = new File(screenshotsDir, fileName);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage(), e);
        }
    }
}
