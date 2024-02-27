package org.geekhub.crewcraft;

import org.geekhub.crewcraft.model.Position;

import java.util.Date;

public interface User {
    String fullName();

    Date birthday();

    String email();

    Position position();

    default String city() {
        return "Unknown";
    }

    String password();
}
