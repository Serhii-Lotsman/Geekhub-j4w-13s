package org.geekhub.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

public class UserRoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public UserRoleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignRole(Long userId, Long roleId) {
        String query = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)";
        String queryForRoleName = "SELECT name FROM roles WHERE id = :roleId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("roleId", roleId);

        try {
            jdbcTemplate.update(query, parameterSource);

            Map<String, Object> roleNameResult = jdbcTemplate.queryForMap(queryForRoleName, parameterSource);
            String roleName = (String) roleNameResult.get("name");

            logger.info("Role '{}' assigned successfully for user with ID: {}, role ID: {}",
                roleName, userId, roleId);
        } catch (DataAccessException e) {
            logger.error("Failed to assign for user with ID: {}, role ID: {}. Error: {}",
                userId, roleId, e.getMessage());
        }
    }

    public void removeRole(Long userId, Long roleId) {
        String query = "DELETE FROM user_roles WHERE user_id = :userId AND role_id = :roleId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("roleId", roleId);

        try {
            jdbcTemplate.update(query, parameterSource);
            logger.info("Role removed successfully for user with ID: {}, role ID: {}",
                userId, roleId);
        } catch (DataAccessException e) {
            logger.error("Failed to remove role for user with ID: {}, role ID: {}. Error: {}",
                userId, roleId, e.getMessage());
        }
    }
}
