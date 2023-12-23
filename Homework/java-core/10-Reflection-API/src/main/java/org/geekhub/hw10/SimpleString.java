package org.geekhub.hw10;

public class SimpleString {
    private String value;

    public SimpleString(String value) {
        this.value = value;
    }

    public String concatenate(SimpleString otherString) {
        if (otherString != null) {
            return this.value.concat(otherString.value);
        } else {
            throw new IllegalArgumentException("Input must be a SimpleString object.");
        }
    }

    public String reverse() {
        return new StringBuilder(this.value).reverse().toString();
    }

    public String uppercase() {
        return this.value.toUpperCase();
    }
}
