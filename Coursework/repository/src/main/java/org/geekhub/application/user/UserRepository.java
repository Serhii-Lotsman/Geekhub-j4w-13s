package org.geekhub.application.user;

import org.geekhub.application.user.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    public static final int INCORRECT_ID = -1;
    private final UserRoleRepository userRoleRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(UserRoleRepository userRoleRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRoleRepository = userRoleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveUser(UserEntity userEntity) {
        String query = "INSERT INTO users (email, password) VALUES (:email, :password) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword()
            ));

        try {
            jdbcTemplate.update(query, parameterSource, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (DataAccessException e) {
            logger.error("Failed to save user with email: {}. Error: {}",
                userEntity.getEmail(), e.getMessage());
            return INCORRECT_ID;
        }
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = :email";

        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        try {
            UserEntity userEntity = jdbcTemplate.queryForObject(query, parameterSource, UserMapper::mapUserEntity);
            logger.info("User found with email: {}", email);
            return Optional.ofNullable(userEntity);
        } catch (DataAccessException e) {
            logger.error("Error finding user with email: {}. Error: {}", email, e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<UserEntity> findUserById(long id) {
        String query = "SELECT * FROM users WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

        try {
            UserEntity userEntity = jdbcTemplate.queryForObject(query, parameterSource, UserMapper::mapUserEntity);
            logger.info("User found with id: {}", id);
            return Optional.ofNullable(userEntity);
        } catch (DataAccessException e) {
            logger.error("Error finding user with id: {}. Error: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    public boolean existsByUserEmail(String email) {
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)";

        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    public void updateUser(UserEntity userEntity) {
        String query = "UPDATE users SET email = :email, password = :password WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword(),
                "id", userEntity.getId() == null ? INCORRECT_ID : userEntity.getId()
            ));

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User updated successfully with new email: {}", userEntity.getEmail());
        } catch (DataAccessException e) {
            logger.error("Failed to update user with email: {}. Error: {}",
                userEntity.getEmail(), e.getMessage());
        }
    }

    public void deleteUser(long id) {
        String query = "DELETE FROM users WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("User deleted successfully with id: {}", id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete user with id: {}. Error: {}", id, e.getMessage());
        }
    }

    public List<UserEntity> findUsers() {
        String query = "SELECT id, email FROM users ORDER BY id";
        List<UserEntity> usersList = new ArrayList<>();

        try {
            usersList = jdbcTemplate.query(
                query,
                (rs, rowNum) -> new UserEntity(
                    rs.getLong("id"),
                    rs.getString("email")
                )
            ).stream().map(user -> {
                user.setRoles(userRoleRepository.getRoles(user.getId()));
                return user;
            }).toList();

            logger.info("Users find successfully");
        } catch (DataAccessException e) {
            logger.error("Failed to find users");
        }

        return usersList;
    }
}
