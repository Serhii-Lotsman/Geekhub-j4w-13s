package org.geekhub.hw10;

import org.geekhub.hw10.test.MathTest;

public class Report {
    private static final String SEPARATOR = "========================================";
    private final TestRunner testRunner;

    public Report() {
        this.testRunner = new TestRunner();
    }

    public void report() {
        System.out.println(SEPARATOR);
        System.out.println("Testing framework");
        System.out.println(SEPARATOR + "\n");
        testRunner.runTestMethods(MathTest.class);
        System.out.println(SEPARATOR);
        System.out.println("Summary");
        System.out.println(SEPARATOR);

    }
}
