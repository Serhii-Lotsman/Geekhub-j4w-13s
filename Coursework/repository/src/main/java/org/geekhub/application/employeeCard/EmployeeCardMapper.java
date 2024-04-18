package org.geekhub.application.employeeCard;

import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeCardMapper {

    private EmployeeCardMapper() {
    }

    public static EmployeeCardEntity mapResultSetToEmployeeRecord(ResultSet rs, int rowNum) throws SQLException {
        EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity();
        employeeCardEntity.setId(rs.getLong("id"));
        employeeCardEntity.setFirstName(rs.getString("first_name"));
        employeeCardEntity.setLastName(rs.getString("last_name"));
        employeeCardEntity.setBirthday(rs.getDate("birthday").toLocalDate());
        employeeCardEntity.setEmail(rs.getString("user_email"));
        employeeCardEntity.setEmployeePosition(EmployeePosition.valueOf(rs.getString("position").toUpperCase()));
        employeeCardEntity.setCity(rs.getString("city"));
        employeeCardEntity.setMarried(rs.getBoolean("is_married"));
        employeeCardEntity.setEmployeeGender(EmployeeGender.valueOf(rs.getString("gender").toUpperCase()));
        employeeCardEntity.setHireDate(rs.getDate("hire_date").toLocalDate());
        return employeeCardEntity;
    }
}
