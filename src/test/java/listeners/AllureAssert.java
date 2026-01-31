package listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.assertj.core.api.SoftAssertions;

public class AllureAssert {

    /**
     * hard assert, stops the test
     */
    public static void assertEquals(Object actual, Object expected, String description) {
        Allure.step(description, () -> {
            try {
                org.testng.Assert.assertEquals(actual, expected, description);
                Allure.step("✓ " + description + " - OK", Status.PASSED);
            } catch (AssertionError e) {
                String errorMsg = String.format("✗ %s - Expected: %s, Actual: %s",
                        description, expected, actual);
                Allure.step(errorMsg, Status.FAILED);
                AllureScreenshotListener.addError(description, errorMsg);
                AllureScreenshotListener.markAsHardFailure();
                throw e;
            }
        });
    }

    /**
     * soft assert, not stops the test
     */
    public static void verifyEquals(Object actual, Object expected, String description) {
        SoftAssertions softly = AllureScreenshotListener.getSoftAssertions();

        Allure.step(description, () -> {
            try {
                softly.assertThat(actual)
                        .as(description)
                        .isEqualTo(expected);
                Allure.step("✓ " + description + " - OK", Status.PASSED);
            } catch (AssertionError e) {
                String errorMsg = String.format("✗ %s - Expected: %s, Actual: %s",
                        description, expected, actual);
                Allure.step(errorMsg, Status.FAILED);
                AllureScreenshotListener.addError(description, errorMsg);
            }
        });
    }

    public static void assertTrue(boolean condition, String description) {
        Allure.step(description, () -> {
            try {
                org.testng.Assert.assertTrue(condition, description);
                Allure.step("✓ " + description + " - OK", Status.PASSED);
            } catch (AssertionError e) {
                String errorMsg = "✗ " + description + " - Condition is not met";
                Allure.step(errorMsg, Status.FAILED);
                AllureScreenshotListener.addError(description, errorMsg);
                AllureScreenshotListener.markAsHardFailure();
                throw e;
            }
        });
    }

    public static void verifyTrue(boolean condition, String description) {
        SoftAssertions softly = AllureScreenshotListener.getSoftAssertions();

        Allure.step(description, () -> {
            try {
                softly.assertThat(condition)
                        .as(description)
                        .isTrue();
                Allure.step("✓ " + description + " - OK", Status.PASSED);
            } catch (AssertionError e) {
                String errorMsg = "✗ " + description + " - Condition is not met";
                Allure.step(errorMsg, Status.FAILED);
                AllureScreenshotListener.addError(description, errorMsg);
            }
        });
    }
}