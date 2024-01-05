package org.geekhub.hw10;


public class SimpleMath {

    public double add(double firstNum, double secondNum) {
        return firstNum + secondNum;
    }

    public double subtract(double firstNum, double secondNum) {
        return firstNum - secondNum;
    }

    public double multiply(double firstNum, double secondNum) {
        return firstNum * secondNum;
    }

    public double divide(double firstNum, double secondNum) {
        if (secondNum != 0) {
            return firstNum / secondNum;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
}
