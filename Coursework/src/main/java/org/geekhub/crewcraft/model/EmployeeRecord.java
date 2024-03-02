package org.geekhub.crewcraft.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record EmployeeRecord(
    @Nullable Long id,
    @NonNull String fullName,
    @NonNull Date birthday,
    @NonNull String email,
    @NonNull EmployeePosition employeePosition,
    @NonNull String password,
    @Nullable String city,
    @NonNull Boolean isMarried,
    @NonNull EmployeeGender employeeGender
) {

    public EmployeeRecord(
        @NonNull String fullName,
        @NonNull Date birthday,
        @NonNull String email,
        @NonNull EmployeePosition employeePosition,
        @NonNull String password,
        @Nullable String city,
        @NonNull Boolean isMarried,
        @NonNull EmployeeGender employeeGender
    ) {
        this(null, fullName, birthday, email, employeePosition, password, city, isMarried, employeeGender);
    }

    public static EmployeeRecordBuilder builder() {
        return new EmployeeRecordBuilder();
    }

    public static class EmployeeRecordBuilder {
        private String fullName;
        private Date birthday;
        private String email;
        private EmployeePosition employeePosition;
        private String password;
        private String city;
        private Boolean isMarried;
        private EmployeeGender employeeGender;

        public EmployeeRecordBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public EmployeeRecordBuilder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public EmployeeRecordBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeRecordBuilder position(EmployeePosition employeePosition) {
            this.employeePosition = employeePosition;
            return this;
        }

        public EmployeeRecordBuilder password(String password) {
            this.password = password;
            return this;
        }

        public EmployeeRecordBuilder city(String city) {
            this.city = city != null ? city : "Unknown";
            return this;
        }

        public EmployeeRecordBuilder isMarried(Boolean isMarried) {
            this.isMarried = isMarried;
            return this;
        }

        public EmployeeRecordBuilder gender(EmployeeGender employeeGender) {
            this.employeeGender = employeeGender;
            return this;
        }

        public EmployeeRecord build() {
            return new EmployeeRecord(fullName, birthday, email, employeePosition, password, city, isMarried, employeeGender);
        }
    }
}
