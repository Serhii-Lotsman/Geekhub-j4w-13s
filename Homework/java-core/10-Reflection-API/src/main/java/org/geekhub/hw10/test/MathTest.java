package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleMath;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class MathTest {
    private SimpleMath math;
    private Assertions assertion;

    //TODO need to impl before and after annotations
    /*@BeforeMethod
    void setUp() {
        math = new SimpleMath();
        assertion = new Assertions();
    }*/

    @Test
    public void testAdd() {
        double result = math.add(3.0, 5.0);
        assertion.assertEquals(8.0, result);
    }

    @Test
    public void testSubtract() {
        double result = math.subtract(8.0, 3.0);
        assertion.assertEquals(5.0, result);
    }

    @Test
    public void testMultiply() {
        double result = math.multiply(3.0, 5.0);
        assertion.assertEquals(15.0, result);
    }

    @Test
    public void testDivide() {
        double result = math.divide(15.0, 3.0);
        assertion.assertEquals(5.0, result);
    }

    @Test
    public void testDivideByZero() {
        Throwable actualThrow = new Throwable();
        try {
            math.divide(10.0, 0.0);
        } catch (Exception e) {
            actualThrow = e.getCause();
        }
        assertion.assertThrows(ArithmeticException.class, actualThrow);
    }

    /*@AfterMethod
    void tearDown() {

    }*/
}
