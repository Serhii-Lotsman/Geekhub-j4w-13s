package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleMath;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

//TODO need to use before() for init fields
public class MathTest {
    private SimpleMath math;
    private Assertions assertion;

    public MathTest() {
        math = new SimpleMath();
        assertion = new Assertions();
    }

    @BeforeMethod
    public void before() {
        System.out.println("Ok");
    }

    @Test
    public String testAdd() {
        double result = math.add(3.0, 5.0);
        return assertion.assertEquals(8.0, result);
    }

    @Test
    public String testSubtract() {
        double result = math.subtract(8.0, 3.0);
        return assertion.assertEquals(5.0, result);
    }

    @Test
    public String testMultiply() {
        double result = math.multiply(3.0, 5.0);
        return assertion.assertEquals(15.0, result);
    }

    @Test
    public String testDivide() {
        double result = math.divide(15.0, 3.0);
        return assertion.assertEquals(5.0, result);
    }

    @Test
    public String testDivideByZero() {
        Throwable actualThrow = null;
        try {
            math.divide(10.0, 0.0);
        } catch (Exception e) {
            actualThrow = e.getCause();
        }
        return assertion.assertThrows(ArithmeticException.class, actualThrow);
    }

    @AfterMethod
    public void after() {
        System.out.println("Tear down");
    }
}
