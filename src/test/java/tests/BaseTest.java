package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ui.pages.MainPage;

public abstract class BaseTest {
    private final static String BASE_URL = "https://www.21vek.by/";

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Browser started and maximized");
    }

    protected MainPage openApp() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.assertIsOpened();
        if (mainPage.isAcceptCookiesButtonVisible()) {
            mainPage.acceptCookies();
        }
        logger.info("Application opened at: " + BASE_URL);
        return mainPage;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}
