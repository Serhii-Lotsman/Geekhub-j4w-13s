package org.geekhub.hw3.orcostat.model.water;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.Orc;

public class Commander extends Orc {
    private Collection rank;

    public Commander() {
        super(50_000);
    }

    public Commander(Collection rank) {
        super(50_000);
        this.rank = rank;
    }

    @Override
    public String scream() {
        return "Noo-ooo!";
    }

    public Collection getRank() {
        return rank;
    }
}
