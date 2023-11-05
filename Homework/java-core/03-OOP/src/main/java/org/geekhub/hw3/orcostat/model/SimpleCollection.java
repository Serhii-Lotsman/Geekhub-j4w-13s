package org.geekhub.hw3.orcostat.model;

import org.geekhub.hw3.orcostat.model.interfaces.Collection;

import java.util.Arrays;

public class SimpleCollection implements Collection {
    private Object[] elements;
    private int size;
    private static final int CAPACITY = 10;

    public SimpleCollection() {
        this.elements = new Object[CAPACITY];
        this.size = 0;
    }

    public SimpleCollection(Object... element) {
        this();
        addAll(element);
    }

    public void addAll(Object... elements) {
        for (Object element : elements) {
            add(element);
        }
    }

    @Override
    public void add(Object element) {
        if (element != null) {
            if (size == elements.length) {
                int newCapacity = elements.length * 2;
                elements = Arrays.copyOf(elements, newCapacity);
            }
            elements[size] = element;
            size++;
        }
    }

    @Override
    public Object[] getElements() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public int size() {
        return size;
    }

}
