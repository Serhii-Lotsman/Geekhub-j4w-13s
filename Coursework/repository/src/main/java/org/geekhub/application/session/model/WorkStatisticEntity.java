package org.geekhub.application.session.model;

import java.time.LocalDate;

public class WorkStatisticEntity {

    private LocalDate date;

    private String userEmail;

    private String monthlyTotalTime;

    private Double salary;

    public WorkStatisticEntity() {
    }

    public WorkStatisticEntity(
        LocalDate date,
        String userEmail,
        String monthlyTotalTime,
        Double salary
    ) {
        this.date = date;
        this.userEmail = userEmail;
        this.monthlyTotalTime = monthlyTotalTime;
        this.salary = salary;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMonthlyTotalTime() {
        return monthlyTotalTime;
    }

    public void setMonthlyTotalTime(String monthlyTotalTime) {
        this.monthlyTotalTime = monthlyTotalTime;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
