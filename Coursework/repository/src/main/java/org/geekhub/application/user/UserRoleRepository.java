package org.geekhub.application.user;

import org.geekhub.application.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRoleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UserRole mapUserRole(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserRole(
            resultSet.getInt("id"),
            resultSet.getString("name")
        );
    }

    public void assignRole(int userId, int roleId) {
        String query = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)";
        String queryForRoleName = "SELECT name FROM roles WHERE id = :roleId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("roleId", roleId);

        try {
            Map<String, Object> roleNameResult = jdbcTemplate.queryForMap(queryForRoleName, parameterSource);
            String roleName = (String) roleNameResult.get("name");
            if (roleName.equals("SUPER_ADMIN")) {
                logger.error("Failed to assign for user SUPER_ADMIN role");
                throw new UserException("Failed to assign this role for user");
            }

            jdbcTemplate.update(query, parameterSource);

            logger.info("Role '{}' assigned successfully for user with ID: {}, role ID: {}",
                roleName, userId, roleId);
        } catch (DataAccessException e) {
            logger.error("Failed to assign for user with ID: {}, role ID: {}. Error: {}",
                userId, roleId, e.getMessage());
        }
    }

    public Optional<UserRole> findRole(String name) {
        String query = "SELECT * FROM roles WHERE name = :name";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("name", name);

        try {
            UserRole userRole = jdbcTemplate.queryForObject(query, parameterSource, this::mapUserRole);
            logger.info("Role found with name: {}", name);
            return Optional.ofNullable(userRole);
        } catch (DataAccessException e) {
            logger.error("Failed to find role with name: {}. Error: {}", name, e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<UserRole> findRole(int id) {
        String query = "SELECT * FROM roles WHERE id = :id";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            UserRole userRole = jdbcTemplate.queryForObject(query, parameterSource, this::mapUserRole);
            logger.info("Role found with id: {}", id);
            return Optional.ofNullable(userRole);
        } catch (DataAccessException e) {
            logger.error("Failed to find role with id: {}. Error: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    public List<UserRole> getRoles(Long id) {
        String roleQuery = "SELECT role_id FROM user_roles WHERE user_id = :userId";

        SqlParameterSource roleParameterSource = new MapSqlParameterSource()
            .addValue("userId", id);

        Integer roleId = jdbcTemplate.queryForObject(
            roleQuery,
            roleParameterSource,
            (rs, rowNum) -> rs.getInt("role_id"));

        UserRole userRole = roleId != null ? findRole(roleId).orElseThrow() : new UserRole();
        return List.of(userRole);
    }

    public void updateRole(Long userId, Long roleId) {
        String query = "UPDATE user_roles SET role_id = :roleId WHERE user_id = :userId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("roleId", roleId)
            .addValue("userId", userId);

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
