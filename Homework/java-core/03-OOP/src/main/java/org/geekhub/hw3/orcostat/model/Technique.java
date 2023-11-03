package org.geekhub.hw3.orcostat.model;

import org.geekhub.hw3.orcostat.model.air.Pilot;
import org.geekhub.hw3.orcostat.model.ground.Driver;

public interface Technique {
    int getPrice();
    Collection getEquipage();
    String shoot();

    default String destroy() {
        StringBuilder agony = new StringBuilder("Destroyed!");
        if (getEquipage().size() > 0) {
            for (Object orc : getEquipage().getElements()) {
                if (orc instanceof Driver driver) {
                    agony.append(driver.scream());
                } else if (orc instanceof Pilot pilot) {
                    agony.append(pilot.scream());
                } else if (orc instanceof Orc meat) {
                    agony.append(meat.scream());
                }
            }
        }
        return agony.toString();
    }
}
