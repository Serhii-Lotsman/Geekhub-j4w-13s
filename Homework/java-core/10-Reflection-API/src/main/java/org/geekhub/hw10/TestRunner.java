package org.geekhub.hw10;

import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;
import org.geekhub.hw10.exception.TestRunnerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TestRunner {
    private Set<Method> methods;
    private int totalTest = 0;
    private int passedTest = 0;
    private int failedTest = 0;

    public TestRunner() {
        this.methods = new HashSet<>();
    }

    public void runTestMethods(Class<?> testClass) {
        methods = Set.of(testClass.getDeclaredMethods());
        Optional<Method> before = methods.stream().filter(method -> method.isAnnotationPresent(BeforeMethod.class)).findFirst();
        Optional<Method> after = methods.stream().filter(method -> method.isAnnotationPresent(AfterMethod.class)).findFirst();
        Object classObject;
        try {
            classObject = testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new TestRunnerException("Error creating an instance of " + testClass.getName(), e);
        }
        System.out.printf("Running tests in %s class %n", classObject.getClass().getSimpleName());

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class) && !Modifier.isStatic(method.getModifiers())) {
                Method beforeMethod = before.orElseGet(before::get);
                Method afterMethod = after.orElseGet(after::get);
                invokeMethod(beforeMethod, classObject);
                printTestResult(method, classObject);
                invokeMethod(afterMethod, classObject);
            }
        }
    }

    private void invokeMethod(Method method, Object instance) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TestRunnerException(e.getMessage(), e.getCause());
        }
    }

    private void printTestResult(Method method, Object instance) {
        try {
            double annotationParam = method.getAnnotation(Test.class).parameterSource();
            Object testResult;
            if (method.getParameterCount() == 0) {
                testResult = method.invoke(instance);
            } else testResult = method.invoke(instance, annotationParam);
            totalTest++;
            if ("Passed".equals(testResult)) {
                System.out.printf("  + Test: %s - %s %n", method.getName(), testResult);
                passedTest++;
            } else {
                System.out.printf("  - Test: %s - %s %n", method.getName(), testResult);
                failedTest++;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TestRunnerException(e.getMessage(), e.getCause());
        }
    }

    public void totalResults() {
        System.out.println("Total tests run: " + totalTest);
        System.out.println("Passed tests: " + passedTest);
        System.out.println("Failed tests: " + failedTest);
    }
}
