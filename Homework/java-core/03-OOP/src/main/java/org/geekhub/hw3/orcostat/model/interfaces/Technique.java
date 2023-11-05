package org.geekhub.hw3.orcostat.model.interfaces;

import org.geekhub.hw3.orcostat.model.Orc;

public interface Technique {
    int getPrice();

    Collection getEquipage();

    String shoot();

    default String destroy() {
        StringBuilder agony = new StringBuilder("Destroyed!");
        for (Object orc : getEquipage().getElements()) {
            if (orc instanceof Orc screamer) {
                agony.append(screamer.scream());
            }
        }
        return agony.toString();
    }
}
