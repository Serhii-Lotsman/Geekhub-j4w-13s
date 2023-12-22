package org.geekhub.hw10;

public class Report {
    private static final String SEPARATOR = "========================================";

    public void report() {
        System.out.println(SEPARATOR);
        System.out.println("Testing framework");
        System.out.println(SEPARATOR + "\n");
        System.out.printf("Running tests in %s class %n", "className");
        System.out.println("");
        System.out.println(SEPARATOR);
        System.out.println("Summary");
        System.out.println(SEPARATOR);

    }
}
