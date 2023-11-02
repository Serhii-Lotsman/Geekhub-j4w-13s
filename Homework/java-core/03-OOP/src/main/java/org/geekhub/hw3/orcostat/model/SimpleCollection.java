package org.geekhub.hw3.orcostat.model;

import java.util.Arrays;

public class SimpleCollection implements Collection {
    private Object[] elements;
    private int size;
    private static final int CAPACITY = 10;

    public SimpleCollection() {
        this.elements = new Object[CAPACITY];
        this.size = 0;
    }

    public SimpleCollection(Object element) {
        this();
        add(element);
    }

    public SimpleCollection(Object element1, Object element2) {
        this();
        add(element1);
        add(element2);
    }

    @Override
    public void add(Object element) {
        if (element != null && !contains(element)) {
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

    private boolean contains(Object element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }
}
