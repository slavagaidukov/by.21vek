package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import ui.pages.MainPage;

public abstract class BaseTest {
    private final static String BASE_URL = "https://www.21vek.by/";

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gaydukovva\\IdeaProjects\\by.21vek\\src\\main\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    protected MainPage openApp() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.assertIsOpened();
        if (mainPage.isAcceptCookiesButtonVisible()) {
            mainPage.acceptCookies();
        }
        return mainPage;
    }

    @AfterClass
    public void tearDown() {
        closeBrowser();
    }

    public void closeBrowser() {
        driver.close();
        driver.quit();
    }


}
