package org.geekhub.crewcraft.repository;

import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeesRepositoryImpl implements EmployeesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeEntity mapResultSetToEmployeeRecord(ResultSet rs, int rowNum) throws SQLException {
        return new EmployeeEntity(
            rs.getLong("id"),
            rs.getString("full_name"),
            rs.getDate("birthday").toLocalDate(),
            rs.getString("email"),
            EmployeePosition.valueOf(rs.getString("position")),
            rs.getString("password"),
            rs.getString("city"),
            rs.getBoolean("is_married"),
            EmployeeGender.valueOf(rs.getString("gender")),
            rs.getDate("hire_date").toInstant().atOffset(ZoneOffset.UTC)
        );
    }

    @Override
    public void saveRecord(@NonNull EmployeeEntity employeeEntity) {
        String query = """
                INSERT INTO employees (full_name, birthday, email, position, password, city, is_married, gender, hire_date)
                VALUES (:fullName, :birthday, :email, :position, :password, :city, :isMarried, :gender, :hireDate)
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "fullName", employeeEntity.fullName(),
                "birthday", employeeEntity.birthday(),
                "email", employeeEntity.email(),
                "position", employeeEntity.employeePosition().name().toLowerCase(),
                "password", employeeEntity.password(),
                "city", employeeEntity.city() != null ? employeeEntity.city() : "Unknown",
                "isMarried", employeeEntity.isMarried(),
                "gender", employeeEntity.employeeGender().name().toLowerCase(),
                "hireDate", employeeEntity.hireDate()
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

    @NonNull
    @Override
    public Optional<EmployeeEntity> getRecord(int id) {
        String query = "SELECT * FROM employees WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        EmployeeEntity employeeEntity = jdbcTemplate.queryForObject(query, params, this::mapResultSetToEmployeeRecord);
        return Optional.ofNullable(employeeEntity);
    }

    @NonNull
    @Override
    public Optional<EmployeeEntity> getRecord(String email) {
        String query = "SELECT * FROM employees WHERE email = :email";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("email", email);

        EmployeeEntity employeeEntity = jdbcTemplate.queryForObject(query, params, this::mapResultSetToEmployeeRecord);
        return Optional.ofNullable(employeeEntity);
    }

    @NonNull
    @Override
    public List<EmployeeEntity> getRecords() {
        String query = "SELECT * FROM employees ORDER BY id";

        return jdbcTemplate.query(query, this::mapResultSetToEmployeeRecord);
    }

    @NonNull
    @Override
    public List<EmployeeEntity> getRecords(String city) {
        String query = "SELECT * FROM employees WHERE city = :column ORDER BY id";
        return getRecordsWithParams(query, city);
    }

    @NonNull
    @Override
    public List<EmployeeEntity> getRecords(EmployeePosition employeePosition) {
        String query = "SELECT * FROM employees WHERE position = :column ORDER BY id";
        return getRecordsWithParams(query, employeePosition);
    }

    @NonNull
    @Override
    public List<EmployeeEntity> getRecords(EmployeeGender employeeGender) {
        String query = "SELECT * FROM employees WHERE gender = :column ORDER BY id";
        return getRecordsWithParams(query, employeeGender);
    }

    @NonNull
    @Override
    public List<EmployeeEntity> getRecords(int pageNum, int pageSize) {
        String query = "SELECT * FROM employees ORDER BY id LIMIT :pageSize OFFSET :offset";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("pageSize", pageSize)
            .addValue("offset", getOffset(pageNum, pageSize));

        return jdbcTemplate.query(query, params, this::mapResultSetToEmployeeRecord);
    }

    private List<EmployeeEntity> getRecordsWithParams(String query, Object columnValue) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("column", columnValue);

        return jdbcTemplate.query(query, params, this::mapResultSetToEmployeeRecord);
    }

    private int getOffset(int pageNum, int pageSize) {
        return pageNum * pageSize - 1;
    }
}
