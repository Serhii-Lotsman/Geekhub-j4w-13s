package org.geekhub.application.session.model;

import org.geekhub.application.enums.EmployeePosition;

public class SalaryEntity {

    private double ratePerHour;
    private double salary;

    public SalaryEntity() {
    }

    public SalaryEntity(double ratePerHour, double salary) {
        this.ratePerHour = ratePerHour;
        this.salary = salary;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(EmployeePosition employeePosition) {
        switch (employeePosition) {
            case ACCOUNTANT -> this.ratePerHour = 120.4;
            case ADMINISTRATOR -> this.ratePerHour = 135.8;
            case CLEANER -> this.ratePerHour = 90.2;
            case CONSULTANT -> this.ratePerHour = 105.8;
            case LAWYER -> this.ratePerHour = 115.6;
            default -> this.ratePerHour = 40;
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
