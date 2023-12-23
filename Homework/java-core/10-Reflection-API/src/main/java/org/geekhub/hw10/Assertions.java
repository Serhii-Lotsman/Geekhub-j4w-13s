package org.geekhub.hw10;

public class Assertions {
    private static final String PASSED = "Passed";
    private static final String FAILED = "Failed";

    public String assertEquals(Object expected, Object actual) {
        if (expected.equals(actual)) {
            return PASSED;
        } else {
            return FAILED + "\n" +
                "      Reason: Expected " + expected + " but got " + actual;
        }
    }

    public String assertThrows(Class<?> expected, Runnable executable) {
        try {
            executable.run();
        } catch (Throwable actualException) {
            if (expected.isInstance(actualException)) {
                return PASSED;
            } else {
                return FAILED + "\n" +
                    "      Reason: Expected " + expected + " but got " + actualException.getClass().getName();
            }
        }
        throw new AssertionError("Expected " + expected.getName() + " but no exception was thrown");
    }

    public String assertReflectionEquals(Object expected, Object actual) {
        return expected.getClass().equals(actual.getClass()) ? PASSED : FAILED;
    }
}
