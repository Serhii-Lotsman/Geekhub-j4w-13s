package org.geekhub.hw10;

import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestRunner {

    //TODO need to fix check before & after
    public void runBeforeMethod(Class<?> testClass) {
        Set<Method> methods = new HashSet<>(List.of(testClass.getDeclaredMethods()));
        Object classObject;
        try {
            classObject = testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating an instance of " + testClass.getName(), e);
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeMethod.class)) {
                try {
                    method.invoke(classObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e.getMessage());
                }
            } else if (method.isAnnotationPresent(AfterMethod.class) && !Modifier.isStatic(method.getModifiers())) {
                try {
                    method.invoke(testClass);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void runTestMethods(Class<?> testClass) {
        Set<Method> methods = new HashSet<>(List.of(testClass.getDeclaredMethods()));
        Object classObject;
        try {
            classObject = testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating an instance of " + testClass.getName(), e);
        }
        System.out.printf("Running tests in %s class %n", classObject.getClass().getSimpleName());

        runBeforeMethod(testClass);

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class) && !Modifier.isStatic(method.getModifiers())) {
                try {
                    Object testResult = method.invoke(classObject);
                    if (testResult.equals("Passed")) {
                        System.out.printf("  + Test: %s - %s %n", method.getName(), testResult);
                    } else {
                        System.out.printf("  - Test: %s - %s %n", method.getName(), testResult);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
