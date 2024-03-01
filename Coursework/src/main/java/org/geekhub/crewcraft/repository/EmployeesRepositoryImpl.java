package org.geekhub.crewcraft.repository;

import org.geekhub.crewcraft.EmployeeRecord;
import org.geekhub.crewcraft.model.GenderEnum;
import org.geekhub.crewcraft.model.PositionEnum;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeesRepositoryImpl implements EmployeesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRecord(@NonNull EmployeeRecord employeeRecord) {
        String query = """
                INSERT INTO employees (full_name, birthday, email, position, password, city, is_married, gender)
                VALUES (:fullName, :birthday, :email, :positionEnum, :password, :city, :isMarried, :genderEnum)
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "fullName", employeeRecord.fullName(),
                "birthday", employeeRecord.birthday(),
                "email", employeeRecord.email(),
                "positionEnum", employeeRecord.positionEnum().name().toLowerCase(),
                "password", employeeRecord.password(),
                "city", employeeRecord.city() != null ? employeeRecord.city() : "Unknown",
                "isMarried", employeeRecord.isMarried(),
                "genderEnum", employeeRecord.genderEnum().name().toLowerCase()
            ));

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteRecord(int id) {
        String query = "DELETE FROM employees WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);
        jdbcTemplate.update(query, params);
    }

    @Override
    public Optional<EmployeeRecord> getRecord(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeRecord> getRecord(String email) {
        return Optional.empty();
    }

    @Override
    public List<EmployeeRecord> getRecords() {
        return null;
    }

    @Override
    public List<EmployeeRecord> getRecords(String city) {
        return null;
    }

    @Override
    public List<EmployeeRecord> getRecords(PositionEnum positionEnum) {
        return null;
    }

    @Override
    public List<EmployeeRecord> getRecords(GenderEnum genderEnum) {
        return null;
    }

    @Override
    public List<EmployeeRecord> getRecords(int pageNum, int pageSize) {
        return null;
    }
}
