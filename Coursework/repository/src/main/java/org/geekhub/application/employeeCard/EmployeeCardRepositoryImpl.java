package org.geekhub.application.employeeCard;

import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeCardRepositoryImpl implements EmployeeCardRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeCardRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeCardRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveEmployeeCard(@NonNull EmployeeCardEntity employeeCardEntity) {
        String query = """
                INSERT INTO employee_card (
                first_name,
                last_name,
                birthday,
                user_email,
                position,
                city,
                is_married,
                gender,
                hire_date
                ) VALUES (
                :firstName,
                :lastName,
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
                "firstName", employeeCardEntity.getFirstName(),
                "lastName", employeeCardEntity.getLastName(),
                "birthday", employeeCardEntity.getBirthday(),
                "email", employeeCardEntity.getEmail(),
                "position", employeeCardEntity.getEmployeePosition().name().toLowerCase(),
                "city", employeeCardEntity.getCity() == null ? "Unknown" : employeeCardEntity.getCity(),
                "isMarried", employeeCardEntity.isMarried(),
                "gender", employeeCardEntity.getEmployeeGender().name().toLowerCase(),
                "hireDate", employeeCardEntity.getHireDate()
            ));

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("Employee card created");
        } catch (DataAccessException e) {
            logger.error("Error creating employee card: {}", e.getMessage());
        }
    }

    @Override
    public void deleteEmployeeCard(Long id) {
        String query = "DELETE FROM employee_card WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("Employee card with id {} was deleted", id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete employee card with id {}. Error: {}", id, e.getMessage());
        }
    }

    @NonNull
    @Override
    public Optional<EmployeeCardEntity> getEmployeeCard(Long id) {
        String query = "SELECT * FROM employee_card WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

        try {
            EmployeeCardEntity employeeCardEntity = jdbcTemplate.queryForObject(
                query,
                parameterSource,
                EmployeeCardMapper::mapEmployeeCard
            );
            return Optional.ofNullable(employeeCardEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @NonNull
    @Override
    public Optional<EmployeeCardEntity> getEmployeeCard(String email) {
        String query = "SELECT * FROM employee_card WHERE user_email = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        try {
            EmployeeCardEntity employeeCardEntity = jdbcTemplate.queryForObject(
                query,
                parameterSource,
                EmployeeCardMapper::mapEmployeeCard
            );
            return Optional.ofNullable(employeeCardEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getEmployeeCards() {
        String query = "SELECT * FROM employee_card ORDER BY id";

        List<EmployeeCardEntity> employeeCardList = new ArrayList<>();

        try {
            employeeCardList = jdbcTemplate.query(query, EmployeeCardMapper::mapEmployeeCard);
            logger.info("Success to get employee cards");
        } catch (DataAccessException e) {
            logger.error("Failed to get employee cards: {}", e.getMessage());
        }
        return employeeCardList;
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getEmployeeCards(String city) {
        String query = "SELECT * FROM employee_card WHERE city = :column ORDER BY id";
        return getRecordsWithParams(query, city);
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getEmployeeCards(EmployeePosition employeePosition) {
        String query = "SELECT * FROM employee_card WHERE position = :column ORDER BY id";
        return getRecordsWithParams(query, employeePosition.name().toLowerCase());
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getEmployeeCards(EmployeeGender employeeGender) {
        String query = "SELECT * FROM employee_card WHERE gender = :column ORDER BY id";
        return getRecordsWithParams(query, employeeGender.name().toLowerCase());
    }

    @NonNull
    @Override
    public List<EmployeeCardEntity> getEmployeeCards(int pageNum, int pageSize) {
        String query = "SELECT * FROM employee_card ORDER BY id LIMIT :pageSize OFFSET :offset";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("pageSize", pageSize)
            .addValue("offset", getOffset(pageNum, pageSize));

        return jdbcTemplate.query(query, params, EmployeeCardMapper::mapEmployeeCard);
    }

    @Override
    public boolean employeeEmailExist(String email) {
        String query = "SELECT EXISTS (SELECT 1 FROM employee_card WHERE user_email = :email)";

        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    @Override
    public void updateEmployeeCard(EmployeeCardEntity employeeCardEntity) {
        String query = """
            UPDATE employee_card SET
                first_name = :firstName,
                last_name = :lastName,
                birthday = :birthday,
                position = :position,
                city = :city,
                is_married = :isMarried,
                gender = :gender,
                hire_date = :hireDate
                WHERE id = :id
                """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "firstName", employeeCardEntity.getFirstName(),
                "lastName", employeeCardEntity.getLastName(),
                "birthday", employeeCardEntity.getBirthday(),
                "position", employeeCardEntity.getEmployeePosition().name().toLowerCase(),
                "city", employeeCardEntity.getCity() == null ? "Unknown" : employeeCardEntity.getCity(),
                "isMarried", employeeCardEntity.isMarried(),
                "gender", employeeCardEntity.getEmployeeGender().name().toLowerCase(),
                "hireDate", employeeCardEntity.getHireDate(),
                "id", employeeCardEntity.getId()
            ));

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("Employee card updated");
        } catch (DataAccessException e) {
            logger.error("Failed updated employee card: {}", e.getMessage());
        }
    }

    private List<EmployeeCardEntity> getRecordsWithParams(String query, Object columnValue) {
        List<EmployeeCardEntity> employeeCardList = new ArrayList<>();

        SqlParameterSource params = new MapSqlParameterSource("column", columnValue);

        try {
            employeeCardList = jdbcTemplate.query(query, params, EmployeeCardMapper::mapEmployeeCard);
            logger.info("Success to get employees");
        } catch (DataAccessException e) {
            logger.error("Error get employees: {}", e.getMessage());
        }
        return employeeCardList;
    }

    private int getOffset(int pageNum, int pageSize) {
        return pageNum * pageSize - 1;
    }
}
