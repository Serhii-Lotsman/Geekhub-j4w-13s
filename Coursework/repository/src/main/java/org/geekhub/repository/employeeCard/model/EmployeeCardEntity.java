package org.geekhub.repository.employeeCard.model;

import org.geekhub.repository.employeeCard.enums.EmployeeGender;
import org.geekhub.repository.employeeCard.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public class EmployeeCardEntity {
    private Integer id;

    private String fullName;

    private LocalDate birthday;

    private String email;

    private EmployeePosition employeePosition;

    @Nullable
    private String city;

    private boolean isMarried;

    private EmployeeGender employeeGender;

    private LocalDate hireDate;

    public EmployeeCardEntity() {
    }

    public EmployeeCardEntity(
        @NonNull Integer id,
        @NonNull String fullName,
        @NonNull LocalDate birthday,
        @NonNull String email,
        @NonNull EmployeePosition employeePosition,
        @Nullable String city,
        boolean isMarried,
        @NonNull EmployeeGender employeeGender,
        @NonNull LocalDate hireDate
    ) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.employeePosition = employeePosition;
        this.city = city;
        this.isMarried = isMarried;
        this.employeeGender = employeeGender;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@NonNull LocalDate birthday) {
        this.birthday = birthday;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(@NonNull EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    @NonNull
    public EmployeeGender getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(@NonNull EmployeeGender employeeGender) {
        this.employeeGender = employeeGender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
