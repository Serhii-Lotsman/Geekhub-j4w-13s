package org.geekhub.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UserEntity mapUserEntity(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserEntity(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
        );
    }

    public void saveUser(UserEntity userEntity) {
        String query = "INSERT INTO users (email, password) VALUES (:email, :password)";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", userEntity.getEmail())
            .addValue("password", userEntity.getPassword());

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User saved successfully with email: {}", userEntity.getEmail());
        } catch (DataAccessException e) {
            logger.error("Failed to save user with email: {}. Error: {}", userEntity.getEmail(), e.getMessage());
        }
    }

    public UserEntity findUserById(Long id) {
        UserEntity userEntity = new UserEntity();
        String query = "SELECT * FROM users WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            userEntity = jdbcTemplate.queryForObject(query, parameterSource, this::mapUserEntity);
            logger.info("User found with ID: {}", id);
        } catch (DataAccessException e) {
            logger.error("Error finding user with ID: {}. Error: {}", id, e.getMessage());
        }
        return userEntity;
    }

    public void updateUser(UserEntity userEntity) {
        String query = "UPDATE users SET email = :email, password = :password WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", userEntity.getEmail())
            .addValue("password", userEntity.getPassword());

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User updated successfully with ID: {}", userEntity.getId());
        } catch (Exception e) {
            logger.error("Failed to update user with ID: {}. Error: {}",
                userEntity.getId(), e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        String query = "DELETE FROM users WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User deleted successfully with ID: {}", id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete user with ID: {}. Error: {}", id, e.getMessage());
        }
    }
}
