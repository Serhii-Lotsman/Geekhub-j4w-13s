package org.geekhub.controller;

import org.geekhub.model.User;
import org.geekhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping( "/users")
    public ModelAndView index(ModelAndView modelAndView) {
        List<User> users = userService.getUsers();
        modelAndView.addAllObjects(Map.of(
            "activeButton", "users",
            "users", users
        ));
        modelAndView.setViewName("users");
        return modelAndView;
    }

}
