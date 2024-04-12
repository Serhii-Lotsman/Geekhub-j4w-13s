package org.geekhub.repository.user;

import org.geekhub.repository.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UserRole mapUserRole(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserRole(
            resultSet.getString("username"),
            Role.valueOf(resultSet.getString("authority").toUpperCase())
        );
    }

    private UserEntity mapUserEntity(ResultSet resultSet, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
            userEntity.setEmail(resultSet.getString("email"));
            userEntity.setPassword(resultSet.getString("password"));
            userEntity.setEnabled(resultSet.getBoolean("enabled"));
            userEntity.setRoles(getRoleList(userEntity.getEmail()));
            return userEntity;
    }

    public void saveUser(UserEntity userEntity) {
        String query = """
        INSERT INTO users (username, password, enabled) VALUES (:email, :password, :enabled)
        """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", userEntity.getEmail())
            .addValue("password", userEntity.getPassword())
            .addValue("enabled", userEntity.isEnabled());

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User saved successfully with email: {}", userEntity.getEmail());
        } catch (DataAccessException e) {
            logger.error("Failed to save user with email: {}. Error: {}", userEntity.getEmail(), e.getMessage());
        }
    }

    private List<Role> getRoleList(String email) {
        List<Role> roleList = new ArrayList<>();
        String query = "SELECT authority FROM authorities WHERE username = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        try {
            roleList =
                Collections.singletonList(jdbcTemplate.queryForObject(
                    query,
                    parameterSource,
                    (rs, rowNum) -> mapUserRole(rs, rowNum).getRole()));
            logger.info("User found with email: {}", email);
        } catch (DataAccessException e) {
            logger.error("Error finding user with email: {}. Error: {}", email, e.getMessage());
        }
        return roleList;
    }

    public UserEntity findUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        String query = "SELECT username FROM users WHERE username = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        try {
            userEntity = jdbcTemplate.queryForObject(query, parameterSource, this::mapUserEntity);
            logger.info("User found with email: {}", email);
        } catch (DataAccessException e) {
            logger.error("Error finding user with email: {}. Error: {}", email, e.getMessage());
        }
        return userEntity;
    }

    public void updateUser(UserEntity userEntity) {
        String query = "UPDATE users SET username = :email, password = :password WHERE username = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", userEntity.getEmail())
            .addValue("password", userEntity.getPassword());

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User updated successfully with new email: {}", userEntity.getEmail());
        } catch (Exception e) {
            logger.error("Failed to update user with email: {}. Error: {}",
                userEntity.getEmail(), e.getMessage());
        }
    }

    public void deleteUser(String email) {
        String query = "DELETE FROM users WHERE username = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User deleted successfully with email: {}", email);
        } catch (DataAccessException e) {
            logger.error("Failed to delete user with email: {}. Error: {}", email, e.getMessage());
        }
    }
}
