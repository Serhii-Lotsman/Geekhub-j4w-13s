package org.geekhub.application.user.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.enums.Role;
import org.geekhub.application.user.UserService;
import org.geekhub.application.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v3/manage/users")
@Tag(name = "assign-roles")
public class UserRoleController {
    private final UserService userService;

    public UserRoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(
        @RequestParam(required = false) int pageNum,
        @RequestParam(required = false) int pageSize
    ) {
        return userService.getUsers(pageNum, pageSize).stream()
            .map(userEntity -> new UserDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getRoles()
            )).toList();
    }

    @PatchMapping("/assign/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateUserRole(@PathVariable long userId, @RequestParam Role role) {
        long roleId = userService.getRoleIdByName(role.name());
        userService.updateRole(userId, roleId);
        return "Role assigned";
    }
}