package helpers;

public class TestUtils {

    /**
     * Pauses execution
     *
     * @param millis Milliseconds
     * @param nanos  Nanoseconds
     * @see Thread#sleep(long, int);
     */
    public static void sleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (Exception ignore) {

        }
    }

    /**
     * Pauses execution
     *
     * @param millis Milliseconds
     * @see Thread#sleep(long);
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignore) {
        }
    }

}

