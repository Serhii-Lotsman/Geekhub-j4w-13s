package org.geekhub.hw3.orcostat.model.water;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.Orc;
import org.geekhub.hw3.orcostat.model.SimpleCollection;
import org.geekhub.hw3.orcostat.model.Technique;

public class Ship implements Technique {
    private final Collection equipage;
    private final int seats;

    public Ship(int seats) {
        this.equipage = new SimpleCollection();
        this.seats = seats;
    }

    @Override
    public int getPrice() {
        return 500_000_000;
    }

    @Override
    public Collection getEquipage() {
        return equipage;
    }

    @Override
    public String shoot() {
        return "Buu-uuu-mm!";
    }

    public boolean putOrc(Orc orc) {
        if (equipage.size() < seats) {
            equipage.add(orc);
            return true;
        }
        return false;
    }
}
