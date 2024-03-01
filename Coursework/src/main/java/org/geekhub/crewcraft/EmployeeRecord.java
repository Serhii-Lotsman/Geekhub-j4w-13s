package org.geekhub.crewcraft;

import org.geekhub.crewcraft.model.GenderEnum;
import org.geekhub.crewcraft.model.PositionEnum;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record EmployeeRecord(
    @Nullable Long id,
    @NonNull String fullName,
    @NonNull Date birthday,
    @NonNull String email,
    @NonNull PositionEnum positionEnum,
    @NonNull String password,
    @Nullable String city,
    @NonNull Boolean isMarried,
    @NonNull GenderEnum genderEnum
) {

    public EmployeeRecord(
        @NonNull String fullName,
        @NonNull Date birthday,
        @NonNull String email,
        @NonNull PositionEnum positionEnum,
        @NonNull String password,
        @Nullable String city,
        @NonNull Boolean isMarried,
        @NonNull GenderEnum genderEnum
    ) {
        this(null, fullName, birthday, email, positionEnum, password, city, isMarried, genderEnum);
    }

    public static EmployeeRecordBuilder builder() {
        return new EmployeeRecordBuilder();
    }

    public static class EmployeeRecordBuilder {
        private Long id;
        private String fullName;
        private Date birthday;
        private String email;
        private PositionEnum positionEnum;
        private String password;
        private String city;
        private Boolean isMarried;
        private GenderEnum genderEnum;

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

        public EmployeeRecordBuilder position(PositionEnum positionEnum) {
            this.positionEnum = positionEnum;
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

        public EmployeeRecordBuilder gender(GenderEnum genderEnum) {
            this.genderEnum = genderEnum;
            return this;
        }

        public EmployeeRecord build() {
            return new EmployeeRecord(fullName, birthday, email, positionEnum, password, city, isMarried, genderEnum);
        }
    }
}
