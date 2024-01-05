package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleMath;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class MathTest {

    private SimpleMath math;
    private Assertions assertion;
    private double firstNum;
    private double secondNum;

    @BeforeMethod
    void setUp() {
        math = new SimpleMath();
        assertion = new Assertions();
        firstNum = 3.0;
        secondNum = 5.0;
    }

    @Test
    public String testAdd() {
        double result = math.add(firstNum, secondNum);
        return assertion.assertEquals(8.0, result - 1);
    }

    @Test(parameterSource = 3.0)
    public String testAddWithParams(double value) {
        double result = math.add(value, secondNum);
        return assertion.assertEquals(8.0, result + 1);
    }

    @Test
    public String testSubtract() {
        double result = math.subtract(secondNum, firstNum);
        return assertion.assertEquals(2.0, result);
    }

    @Test
    public String testMultiply() {
        double result = math.multiply(firstNum, secondNum);
        return assertion.assertEquals(15.0, result - 1);
    }

    @Test
    public String testDivide() {
        double result = math.divide(15.0, firstNum);
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

    @Test
    public String testReflection() {
        return assertion.assertReflectionEquals(SimpleMath.class, math.getClass());
    }

    @Test
    public String testReflectionFail() {
        return assertion.assertReflectionEquals(SimpleMath.class, math.getClass().getSimpleName());
    }

    @AfterMethod
    void tearDown() {
        firstNum = 0.0;
        secondNum = 0.0;
    }
}
