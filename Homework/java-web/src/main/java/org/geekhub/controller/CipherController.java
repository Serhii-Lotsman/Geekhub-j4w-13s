package org.geekhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CipherController {
    @GetMapping({"/", "/index.html"})
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("activeButton", "home");
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
