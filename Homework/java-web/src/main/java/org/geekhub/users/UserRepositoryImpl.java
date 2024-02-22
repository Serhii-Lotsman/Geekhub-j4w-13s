package org.geekhub.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User userMap(ResultSet rs, int rowNum) throws SQLException {
        return new User(
            rs.getInt("user_id"),
            rs.getString("username")
        );
    }

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT * FROM users WHERE user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("userId", userId);

        try {
            User user = jdbcTemplate.queryForObject(query, params, this::userMap);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
