package org.geekhub.hw10;

public class Report {

    private static final String SEPARATOR = "========================================";
    private final TestInvoker testInvoker = new TestInvoker();

    public void report() {
        System.out.println(SEPARATOR);
        System.out.println("Testing framework");
        System.out.println(SEPARATOR + "\n");
        testInvoker.runTest();
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("Summary");
        System.out.println(SEPARATOR);
        testInvoker.runTestResult();
    }
}
