package org.geekhub.service;

import org.geekhub.annotation.Injectable;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectableExecutor {

    @SuppressWarnings("java:S3011")
    public String getAnnotatedFieldValue(Class<?> encryptClass) {
        Set<Field> declaredFields = Set.of(encryptClass.getDeclaredFields());
        String getValue = null;

        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Injectable.class))
                getValue = field.getAnnotation(Injectable.class).property();
        }
        return getValue;
    }
}
