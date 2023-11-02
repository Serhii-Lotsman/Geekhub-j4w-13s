package org.geekhub.hw3.orcostat.model.air;

import org.geekhub.hw3.orcostat.model.Orc;

public class Pilot extends Orc {

    private int flewHours;

    public Pilot() {
    }

    public Pilot(int flewHours) {
        super(20_000);
        this.flewHours = flewHours;
    }

    @Override
    public String scream() {
        return "Ohh noo-oo-o";
    }

    public int getFlewHours() {
        return flewHours;
    }
}
