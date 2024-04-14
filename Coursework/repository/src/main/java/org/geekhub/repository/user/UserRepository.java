package org.geekhub.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRoleRepository userRoleRepository;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRoleRepository userRoleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRoleRepository = userRoleRepository;
    }

    private UserEntity mapUserEntity(ResultSet resultSet, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt("id"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setPassword(resultSet.getString("password"));
        userEntity.setRoles(userRoleRepository.getRole(userEntity));
        return userEntity;
    }

    public int saveUser(UserEntity userEntity) {
        String query = "INSERT INTO users (email, password) VALUES (:email, :password) RETURNING id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword()
            ));

        try {
            Integer generatedId = jdbcTemplate.queryForObject(query, parameterSource, Integer.class);
            return generatedId != null ? generatedId : -1;
        } catch (DataAccessException e) {
            logger.error("Failed to save user with email: {}. Error: {}",
                userEntity.getEmail(), e.getMessage());
            return -1;
        }
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        String query = "SELECT * FROM users WHERE email = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        try {
            userEntity = jdbcTemplate.queryForObject(query, parameterSource, this::mapUserEntity);
            logger.info("User found with email: {}", email);
        } catch (DataAccessException e) {
            logger.error("Error finding user with email: {}. Error: {}", email, e.getMessage());
        }
        return Optional.ofNullable(userEntity);
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        try {
            Integer count = jdbcTemplate.queryForObject(query, parameterSource, Integer.class);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void updateUser(UserEntity userEntity) {
        String query = "UPDATE users SET email = :email, password = :password WHERE email = :email";

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
        String query = "DELETE FROM users WHERE email = :email";

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
