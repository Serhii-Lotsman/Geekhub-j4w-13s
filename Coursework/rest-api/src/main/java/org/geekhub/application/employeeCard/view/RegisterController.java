package org.geekhub.application.employeeCard.view;

import jakarta.validation.Valid;
import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.converter.EmployeeCardConverter;
import org.geekhub.application.converter.RegisterDtoConverter;
import org.geekhub.application.employeeCard.EmployeeCardService;
import org.geekhub.application.user.UserService;
import org.geekhub.application.user.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    private final UserService userService;
    private final EmployeeCardService employeeCardService;
    private final RegisterDtoConverter registerDtoConverter;

    public RegisterController(
        UserService userService,
        EmployeeCardService employeeCardService,
        RegisterDtoConverter registerDtoConverter
    ) {
        this.userService = userService;
        this.employeeCardService = employeeCardService;
        this.registerDtoConverter = registerDtoConverter;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterForm(ModelAndView modelAndView, RegisterDto registerDto) {
        modelAndView.addObject("registerDto", registerDto);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(
        @Valid
        ModelAndView modelAndView,
        @RequestBody RegisterDto registerDto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        }


        UserEntity userEntity = registerDtoConverter.registerFromDto(registerDto);
        int userId = userService.createUser(userEntity);
        userService.setUserRole(userId, userEntity.getRoles().get(0).getId());
        employeeCardService.saveEmployee(EmployeeCardConverter.employeeFromDto(registerDto));
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
