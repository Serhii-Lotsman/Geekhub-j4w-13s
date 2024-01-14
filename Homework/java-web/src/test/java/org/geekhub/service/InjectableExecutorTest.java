package org.geekhub.service;

import org.geekhub.annotation.Injectable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectableExecutorTest {
    InjectableExecutor injector;
    @BeforeEach
    void setUp() {
        this.injector = new InjectableExecutor();
    }

    @Test
    void getAnnotatedFieldValue_whenAnnotatedFieldValueValid_shouldReturnValue() {
        class TestClass {
            @Injectable(property = "value2")
            private String field;
        }

        String result = injector.getAnnotatedFieldValue(TestClass.class);
        assertEquals("value2", result);
    }
}
