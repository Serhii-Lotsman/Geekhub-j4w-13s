package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleMath;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class MathTest {
    private SimpleMath math;
    private Assertions assertion;

    @BeforeMethod
    void setUp() {
        math = new SimpleMath();
        assertion = new Assertions();
    }

    @Test
    public String testAdd() {
        double result = math.add(3.0, 5.0);
        return assertion.assertEquals(8.0, result - 1);
    }

    @Test
    public String testSubtract() {
        double result = math.subtract(8.0, 3.0);
        return assertion.assertEquals(5.0, result);
    }

    @Test
    public String testMultiply() {
        double result = math.multiply(3.0, 5.0);
        return assertion.assertEquals(15.0, result - 1);
    }

    @Test
    public String testDivide() {
        double result = math.divide(15.0, 3.0);
        return assertion.assertEquals(5.0, result);
    }

    @Test
    public String testDivideByZero() {
        return assertion.assertThrows(ArithmeticException.class, () -> math.divide(10.0, 0.0));
    }

    @Test
    public String testException() {
        return assertion.assertThrows(IllegalArgumentException.class, () -> math.divide(10.0, 0.0));
    }

    @AfterMethod
    void tearDown() {
    }
}
