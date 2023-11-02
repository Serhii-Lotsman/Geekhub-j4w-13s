package org.geekhub.hw3.orcostat.model.ground;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.DriverLicenseCategory;
import org.geekhub.hw3.orcostat.model.Orc;
import org.geekhub.hw3.orcostat.model.SimpleCollection;

public class Driver extends Orc {

    private final Collection licenseCategories;

    public Driver() {
        this(new SimpleCollection(DriverLicenseCategory.values()));
    }

    public Driver(Collection licenseCategories) {
        super(15_000);
        this.licenseCategories = licenseCategories;
    }

    public Collection getLicenseCategories() {
        return licenseCategories;
    }

    @Override
    public String scream() {
        return "Crap!";
    }
}
