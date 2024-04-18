package org.geekhub.application.user;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
