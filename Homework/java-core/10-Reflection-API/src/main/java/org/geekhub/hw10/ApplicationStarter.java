package org.geekhub.hw10;

import org.geekhub.hw10.test.MathTest;

public class ApplicationStarter {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.runTestMethods(MathTest.class);
    }
}
