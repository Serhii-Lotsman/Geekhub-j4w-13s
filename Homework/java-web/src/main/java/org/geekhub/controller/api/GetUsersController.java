package org.geekhub.controller.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.model.User;
import org.geekhub.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v3/users")
@Tag(name="get-users-controller", description = "Get users or user by id")
public class GetUsersController {

    private final UserService userService;

    public GetUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping( "/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User with id successfully found"),
        @ApiResponse(responseCode = "500", description = "This user's id doesn't exist")
    })
    public User getUserById(@PathVariable long id) {
        return userService.getUser(id);
    }

}
