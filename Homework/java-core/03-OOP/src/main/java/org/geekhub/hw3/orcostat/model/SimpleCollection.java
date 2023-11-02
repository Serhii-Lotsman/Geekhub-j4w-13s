package org.geekhub.hw3.orcostat.model;

public class SimpleCollection implements Collection {
    protected Object object;
    public SimpleCollection() {
    }
    public SimpleCollection(Object object) {
        this.object = object;
    }

    @Override
    public void add(Object element) {

    }

    @Override
    public Object[] getElements() {
        return new Object[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
