package org.geekhub.hw3.orcostat.model;

import org.geekhub.hw3.orcostat.model.interfaces.Technique;

public class OrcoStat {
    private int orcCounter;
    private int dollarsCounter;

    public void smashOrc(Orc orc) {
        orcCounter++;
        dollarsCounter += orc.getPrice();
    }

    public int getNegativelyAliveOrcCount() {
        return orcCounter;
    }

    public void smashTechnique(Technique technique) {
        dollarsCounter += technique.getPrice();
        for (Object object : technique.getEquipage().getElements()) {
            smashOrc((Orc) object);
        }
    }

    public int getLosesInDollars() {
        return dollarsCounter;
    }
}
