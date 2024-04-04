package org.geekhub.repository.employeeCard;

import org.geekhub.repository.employeeCard.enums.EmployeeGender;
import org.geekhub.repository.employeeCard.enums.EmployeePosition;
import org.geekhub.repository.employeeCard.model.EmployeeCardEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeCardRepositoryImpl implements EmployeeCardRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeCardRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeCardEntity mapResultSetToEmployeeRecord(ResultSet rs, int rowNum) throws SQLException {
        return new EmployeeCardEntity(
            rs.getLong("id"),
            rs.getString("full_name"),
            rs.getDate("birthday").toLocalDate(),
            rs.getString("email"),
            EmployeePosition.valueOf(rs.getString("position").toUpperCase()),
            rs.getString("city"),
            rs.getBoolean("is_married"),
            EmployeeGender.valueOf(rs.getString("gender").toUpperCase()),
            rs.getDate("hire_date").toLocalDate()
        );
    }

    @Override
    public void saveEmployee(@NonNull EmployeeCardEntity employeeCardEntity) {
        String query = """
                    INSERT INTO employee_card (
                    full_name,
                    birthday,
                    email,
                    position,
                    city,
                    is_married,
                    gender,
                    hire_date
                    )
                    VALUES (
                    :fullName,
                    :birthday,
                    :email,
                    :position,
                    :city,
                    :isMarried,
                    :gender,
                    :hireDate
                    )
                """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "fullName", employeeCardEntity.fullName(),
                "birthday", employeeCardEntity.birthday(),
                "email", employeeCardEntity.email(),
                "position", employeeCardEntity.employeePosition().name().toLowerCase(),
                "city", employeeCardEntity.city() != null ? employeeCardEntity.city() : "Unknown",
                "isMarried", employeeCardEntity.isMarried(),
                "gender", employeeCardEntity.employeeGender().name().toLowerCase(),
                "hireDate", employeeCardEntity.hireDate()
            ));

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteRecord(int id) {
        String query = "DELETE FROM employee_card WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        jdbcTemplate.update(query, params);
    }

    @NonNull
    @Override
    public Optional<EmployeeCardEntity> getRecord(int id) {
        String query = "SELECT * FROM employee_card WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        EmployeeCardEntity employeeCardEntity = jdbcTemplate.queryForObject(query, params, this::mapResultSetToEmployeeRecord);
        return Optional.ofNullable(employeeCardEntity);
    }

    @NonNull
    @Override
    public Optional<EmployeeCardEntity> getRecord(String email) {
        String query = "SELECT * FROM employee_card WHERE email = :email";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("email", email);

        EmployeeCardEntity employeeCardEntity = jdbcTemplate.queryForObject(query, params, this::mapResultSetToEmployeeRecord);
        return Optional.ofNullable(employeeCardEntity);
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getRecords() {
        String query = "SELECT * FROM employee_card ORDER BY id";

        return jdbcTemplate.query(query, this::mapResultSetToEmployeeRecord);
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getRecords(String city) {
        String query = "SELECT * FROM employee_card WHERE city = :column ORDER BY id";
        return getRecordsWithParams(query, city);
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getRecords(EmployeePosition employeePosition) {
        String query = "SELECT * FROM employee_card WHERE position = :column ORDER BY id";
        return getRecordsWithParams(query, employeePosition.name().toLowerCase());
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getRecords(EmployeeGender employeeGender) {
        String query = "SELECT * FROM employee_card WHERE gender = :column ORDER BY id";
        return getRecordsWithParams(query, employeeGender.name().toLowerCase());
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getRecords(int pageNum, int pageSize) {
        String query = "SELECT * FROM employee_card ORDER BY id LIMIT :pageSize OFFSET :offset";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("pageSize", pageSize)
            .addValue("offset", getOffset(pageNum, pageSize));

        return jdbcTemplate.query(query, params, this::mapResultSetToEmployeeRecord);
    }

    private List<EmployeeCardEntity> getRecordsWithParams(String query, Object columnValue) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("column", columnValue);

        return jdbcTemplate.query(query, params, this::mapResultSetToEmployeeRecord);
    }

    private int getOffset(int pageNum, int pageSize) {
        return pageNum * pageSize - 1;
    }
}
