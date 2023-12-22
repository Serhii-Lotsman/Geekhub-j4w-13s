package org.geekhub.hw10;

public class Assertions {

    public boolean assertEquals(Object expected, Object got) {
        return expected.equals(got);
    }

    public boolean assertThrows(Class<?> expectedType, Throwable throwable) {
        return expectedType.isInstance(throwable);
    }
}
