package org.geekhub.hw10.test;

import org.geekhub.hw10.Assertions;
import org.geekhub.hw10.SimpleString;
import org.geekhub.hw10.annotations.AfterMethod;
import org.geekhub.hw10.annotations.BeforeMethod;
import org.geekhub.hw10.annotations.Test;

public class StringUtilsTest {
    private Assertions assertion;
    private SimpleString string1;
    private SimpleString string2;

    @BeforeMethod
    void setUp() {
        this.assertion = new Assertions();
        this.string1 = new SimpleString("Hello, ");
        this.string2 = new SimpleString("world!");
    }

    @Test
    public String testConcatenate() {
        return assertion.assertEquals("Hello, world!", string1.concatenate(string2.toString()));
    }

    @Test
    public String testReverse() {
        return assertion.assertEquals(" ,olleH", string1.reverse());
    }

    @Test
    public String testUppercase() {
        return assertion.assertEquals("WORLD", string2.uppercase());
    }

    @Test(parameterSource = 3.3)
    public String testConcatWithParams(double value) {
        return assertion.assertEquals("Hello, 3.3", string1.concatenate(String.valueOf(value)));
    }

    @Test
    public String testReflection() {
        return assertion.assertReflectionEquals(StringUtilsTest.class, string1.getClass());
    }

    @AfterMethod
    void tearDown() {
        string1 = null;
        string2 = null;
    }
}
