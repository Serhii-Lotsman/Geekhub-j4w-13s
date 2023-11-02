package org.geekhub.hw3.orcostat.model.air;

import org.geekhub.hw3.orcostat.model.Orc;

public class Pilot extends Orc {
    public Pilot() {
        super();
    }

    public Pilot(int price) {
        super(price);
    }

    @Override
    public String scream() {
        return "Ohh noo-oo-o";
    }

    @Override
    public void setPrice(int price) {
        super.setPrice(price);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    public int getFlewHours() {
        return flewHours;
    }
}
