package org.geekhub.hw10;

import org.geekhub.hw10.test.MathTest;
import org.geekhub.hw10.test.StringUtilsTest;

public class TestInvoker {

    private final TestRunner testRunner;

    public TestInvoker() {
        this.testRunner = new TestRunner();
    }

    public void runTest() {
        testRunner.runTestMethods(MathTest.class);
        testRunner.runTestMethods(StringUtilsTest.class);
    }

    public void runTestResult() {
        testRunner.totalResults();
    }

}
