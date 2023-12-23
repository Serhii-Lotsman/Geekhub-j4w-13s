package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleMath;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class MathTest {
    private SimpleMath math;
    private Assertions assertion;
    private double operand1;
    private double operand2;

    @BeforeMethod
    void setUp() {
        math = new SimpleMath();
        assertion = new Assertions();
        operand1 = 3.0;
        operand2 = 5.0;
    }

    @Test
    public String testAdd() {
        double result = math.add(operand1, operand2);
        return assertion.assertEquals(8.0, result - 1);
    }

    @Test(parameterSource = 3.0)
    public String testAddWithParams(double value) {
        double result = math.add(value, operand2);
        return assertion.assertEquals(8.0, result + 1);
    }

    @Test
    public String testSubtract() {
        double result = math.subtract(operand2, operand1);
        return assertion.assertEquals(2.0, result);
    }

    @Test
    public String testMultiply() {
        double result = math.multiply(operand1, operand2);
        return assertion.assertEquals(15.0, result - 1);
    }

    @Test
    public String testDivide() {
        double result = math.divide(15.0, operand1);
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
        operand1 = 0.0;
        operand2 = 0.0;
    }
}
