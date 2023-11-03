package org.geekhub.hw3.orcostat.model.air;

import org.geekhub.hw3.orcostat.model.Orc;

public class Pilot extends Orc {

    private int flewHours;

    public Pilot() {
        super(20_000);
    }

    public Pilot(int flewHours) {
        super(20_000);
        this.flewHours = flewHours;
    }

    @Override
    public String scream() {
        return "Hell!";
    }

    public int getFlewHours() {
        return flewHours;
    }
}
