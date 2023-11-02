package org.geekhub.hw3.orcostat.model;

public class Orc {
    private int price = 10_000;

    public Orc() {
    }

    public Orc(int price) {
        this.price = price;
    }

    public String scream() {
        return "Aaaaaa!";
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}
