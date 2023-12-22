package org.geekhub.hw10;


public class SimpleMath {

    public double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    public double subtract(double operand1, double operand2) {
        return operand1 - operand2;
    }

    public double multiply(double operand1, double operand2) {
        return operand1 * operand2;
    }

    public double divide(double dividend, double divisor) {
        if (divisor != 0) {
            return dividend / divisor;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
}
