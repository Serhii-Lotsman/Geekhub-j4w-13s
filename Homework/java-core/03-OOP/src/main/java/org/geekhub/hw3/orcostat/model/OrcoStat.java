package org.geekhub.hw3.orcostat.model;

public class OrcoStat {
    private int orcCounter;
    private int dollarsCounter;

    public void smashOrc(Orc orc) {
        orc.scream();
        orcCounter++;
        dollarsCounter += orc.getPrice();
    }

    public int getNegativelyAliveOrcCount() {
        return orcCounter;
    }

    public void smashTechnique(Technique technique) {
        technique.destroy();
        dollarsCounter += technique.getPrice();
        if (technique.getEquipage().size() != 0) {
            for (Object object : technique.getEquipage().getElements()) {
                smashOrc((Orc) object);
            }
        }
    }

    public int getLosesInDollars() {
        return dollarsCounter;
    }
}
