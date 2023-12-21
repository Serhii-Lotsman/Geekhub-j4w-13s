package org.geekhub.hw10;

public class Report {
    private static final String SEPARATOR = "========================================";

    public void report() {
        System.out.println(SEPARATOR);
        System.out.println("Testing framework");
        System.out.println(SEPARATOR + "\n");
        getTestResult("obj");
        System.out.println("");
        System.out.println(SEPARATOR);
        System.out.println("Summary");
        System.out.println(SEPARATOR);

    }

    /*private Object initializeObject(Object object) {
        Class<?> clazz = object.getClass();
        String[] res = new String[3];
        int i = 0;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                method.setAccessible(true);
                res[i] = method.getName();
                i++;
            }
        }
        return res;
    }*/

    private void getTestResult(Object object) {
        Object className = object.getClass().getSimpleName();
        System.out.printf("Running tests in %s class %n", className);
    }
}
