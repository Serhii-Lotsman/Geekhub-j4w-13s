package org.geekhub.hw10;

import org.geekhub.hw10.annotations.Test;

public class MathTest {
    @Test
    public double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    @Test
    public double subtract(double operand1, double operand2) {
        return operand1 - operand2;
    }

    @Test
    public double multiply(double operand1, double operand2) {
        return operand1 * operand2;
    }

    @Test
    public double divide(double dividend, double divisor) {
        if (divisor != 0) {
            return dividend / divisor;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
}
