package org.geekhub.controller;

import org.geekhub.model.User;
import org.geekhub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getUsers(ModelAndView modelAndView) {
        List<User> users = userService.getUsers();
        modelAndView.addAllObjects(Map.of(
            "activeButton", "users",
            "users", users
        ));
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getUserById(ModelAndView modelAndView, @PathVariable long id) {
        User user = userService.getUser(id);
        modelAndView.addAllObjects(Map.of(
            "activeButton", "users",
            "user", user
        ));
        modelAndView.setViewName("userById");
        return modelAndView;
    }

}
