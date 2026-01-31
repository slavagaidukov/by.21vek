package listeners;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.assertj.core.api.SoftAssertions;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import tests.BaseTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AllureScreenshotListener implements ITestListener {

    private static final ThreadLocal<Boolean> hasHardFailure = new ThreadLocal<>();    // Храним SoftAssertions для каждого потока
    private static final ThreadLocal<SoftAssertions> softAssertions = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, String>> testErrors = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        softAssertions.set(new SoftAssertions());
        hasHardFailure.set(false);
    }

    public static void markAsHardFailure() {
        hasHardFailure.set(true);
    }

    public static boolean hasHardFailure() {
        return Boolean.TRUE.equals(hasHardFailure.get());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            takeScreenshot(driver, "Error: " + result.getName());
        }

        if (result.getThrowable() != null) {
            String errorMessage = result.getThrowable().getMessage();
            Allure.step("Test failed: " + errorMessage, Status.FAILED);
            Allure.addAttachment("Error", "text/plain", errorMessage);
        }

        checkSoftAssertionsErrors(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            takeScreenshot(driver, "Success: " + result.getName());
        }

        checkSoftAssertionsErrors(result);

        if (result.getStatus() == ITestResult.SUCCESS) {
            Allure.step("Test passed", Status.PASSED);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            takeScreenshot(driver, "Skipped: " + result.getName());
        }
        Allure.step("Test skipped: " + result.getName(), Status.BROKEN);
    }

    private void checkSoftAssertionsErrors(ITestResult result) {
        try {
            SoftAssertions softly = softAssertions.get();
            if (softly != null) {
                softly.assertAll(); // Проверяем все мягкие утверждения
            }
        } catch (AssertionError e) {
            // Если есть ошибки в Soft Assertions, помечаем тест как упавший
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);

            // Добавляем детали ошибок в Allure
            String errorDetails = e.getMessage();
            Allure.step("Test has uncritical errors", Status.FAILED);
            Allure.addAttachment("Errors", "text/plain", errorDetails);

            // Делаем дополнительный скриншот
            WebDriver driver = getDriverFromTest(result);
            if (driver != null) {
                takeScreenshot(driver, "Errors: " + result.getName());
            }
        } finally {
            // Очищаем ThreadLocal
            softAssertions.remove();
            testErrors.remove();
        }
    }


    public static SoftAssertions getSoftAssertions() {
        SoftAssertions softly = softAssertions.get();
        if (softly == null) {
            softly = new SoftAssertions();
            softAssertions.set(softly);
        }
        return softly;
    }

    public static void addError(String stepName, String errorMessage) {
        Map<String, String> errors = testErrors.get();
        if (errors == null) {
            errors = new HashMap<>();
            testErrors.set(errors);
        }
        errors.put(stepName, errorMessage);
    }


    public static void stepWithAssertion(String stepName, Runnable assertionCode) {
        Allure.step(stepName, () -> {
            try {
                assertionCode.run();
                Allure.step("✓ Verification passed", Status.PASSED);
            } catch (AssertionError e) {
                String errorMsg = "✗ Verification failed: " + e.getMessage();
                addError(stepName, errorMsg);
                Allure.step(errorMsg, Status.FAILED);
                throw e;
            }
        });
    }

    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                BaseTest baseTest = (BaseTest) testInstance;
                Field driverField = BaseTest.class.getDeclaredField("driver");
                driverField.setAccessible(true);
                ThreadLocal<WebDriver> driverThreadLocal = (ThreadLocal<WebDriver>) driverField.get(baseTest);
                return driverThreadLocal.get();
            }
        } catch (Exception e) {
            System.err.println("Failed to get WebDriver: " + e.getMessage());
        }
        return null;
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver, String screenshotName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}