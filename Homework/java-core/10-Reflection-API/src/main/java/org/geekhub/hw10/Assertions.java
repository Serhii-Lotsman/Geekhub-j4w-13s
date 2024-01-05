package org.geekhub.hw10;

public class Assertions {

    private static final String PASSED = "Passed";
    private static final String FAILED = "Failed \n" + "      Reason: Expected ";
    private static final String GOT = " but got ";

    public String assertEquals(Object expected, Object actual) {
        return expected.equals(actual) ?
            PASSED :
            FAILED + expected + GOT + actual;
    }

    public String assertThrows(Class<?> expected, Runnable executable) {
        try {
            executable.run();
        } catch (Exception actualException) {
            return expected.isInstance(actualException) ?
                PASSED :
                FAILED + expected + GOT + actualException.getClass().getName();
        }
        throw new AssertionError("Expected " + expected.getName() + " but no exception was thrown");
    }

    public String assertReflectionEquals(Object expected, Object actual) {
        return expected.getClass().equals(actual.getClass()) ?
            PASSED :
            FAILED + expected + GOT + actual;
    }
}
