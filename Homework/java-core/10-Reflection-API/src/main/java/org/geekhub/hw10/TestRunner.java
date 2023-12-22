package org.geekhub.hw10;

import org.geekhub.hw10.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestRunner {

    //TODO need to fix exceptions throws + passedCounter, failedCounter ect.
    public void runTestMethods(Class<?> testClass) {
        Object classObject;
        try {
            classObject = testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating an instance of " + testClass.getName(), e);
        }

        System.out.printf("Running tests in %s class %n", classObject.getClass().getSimpleName());
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class) && !Modifier.isStatic(method.getModifiers())) {
                try {
                    method.invoke(classObject);
                    System.out.printf("  + Test: %s - Passed %n", method.getName());
                } catch (Exception e) {
                    System.out.printf("  - Test: %s - Failed %n", method.getName());
                    e.printStackTrace();
                }
            }
        }
    }
}
