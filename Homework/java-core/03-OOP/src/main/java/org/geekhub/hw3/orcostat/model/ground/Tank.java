package org.geekhub.hw3.orcostat.model.ground;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.Orc;
import org.geekhub.hw3.orcostat.model.SimpleCollection;
import org.geekhub.hw3.orcostat.model.Technique;

public class Tank implements Technique {
    private final Collection equipage;
    private int seats = 6;

    public Tank(int seats) {
        if (seats > this.seats || seats < 0) {
            seats = this.seats;
        } else this.seats = seats;
        this.equipage = new SimpleCollection(seats);
    }

    @Override
    public int getPrice() {
        return 3_000_000;
    }

    @Override
    public Collection getEquipage() {
        return equipage;
    }

    @Override
    public String shoot() {
        return "Bam!";
    }

    @Override
    public String destroy() {
        return "Destroyed!";
    }

    public boolean putOrc(Orc orc) {
        if (equipage.size() < 6) {
            equipage.add(orc);
            return true;
        }
        return false;
    }
}
