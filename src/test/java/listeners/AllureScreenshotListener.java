package listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class AllureScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            takeScreenshot(driver, "Ошибка: " + result.getName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            takeScreenshot(driver, "Успех: " + result.getName());
        }
    }

    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            Object testInstance = result.getInstance();

            if (testInstance instanceof BaseTest) {
                BaseTest baseTest = (BaseTest) testInstance;

                java.lang.reflect.Field driverField = BaseTest.class.getDeclaredField("driver");
                driverField.setAccessible(true);

                ThreadLocal<WebDriver> driverThreadLocal = (ThreadLocal<WebDriver>) driverField.get(baseTest);
                return driverThreadLocal.get();
            }
        } catch (Exception e) {
            System.err.println("Не удалось получить WebDriver: " + e.getMessage());
        }
        return null;
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver, String screenshotName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
