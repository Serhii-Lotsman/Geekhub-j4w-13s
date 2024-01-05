package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleString;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class StringUtilsTest {

    private Assertions assertion;
    private SimpleString firstString;
    private SimpleString secondString;

    @BeforeMethod
    void setUp() {
        this.assertion = new Assertions();
        this.firstString = new SimpleString("Hello, ");
        this.secondString = new SimpleString("world!");
    }

    @Test
    public String testConcatenate() {
        return assertion.assertEquals("Hello, world!", firstString.concatenate(secondString.toString()));
    }

    @Test
    public String testReverse() {
        return assertion.assertEquals(" ,olleH", firstString.reverse());
    }

    @Test
    public String testUppercase() {
        return assertion.assertEquals("WORLD", secondString.uppercase());
    }

    @Test(parameterSource = 3.3)
    public String testConcatWithParams(double value) {
        return assertion.assertEquals("Hello, 3.3", firstString.concatenate(String.valueOf(value)));
    }

    @Test
    public String testReflection() {
        return assertion.assertReflectionEquals(StringUtilsTest.class, firstString.getClass());
    }

    @AfterMethod
    void tearDown() {
        firstString = null;
        secondString = null;
    }
}
