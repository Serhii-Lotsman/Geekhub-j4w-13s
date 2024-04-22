package org.geekhub.application.user;

import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("java:S1172")
public class UserMapper {

    private UserMapper() {
    }

    public static UserEntity mapUserEntity(ResultSet resultSet, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getLong("id"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setPassword(resultSet.getString("password"));
        return userEntity;
    }

    public static UserRole mapUserRole(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserRole(
            resultSet.getInt("id"),
            resultSet.getString("name")
        );
    }
}
