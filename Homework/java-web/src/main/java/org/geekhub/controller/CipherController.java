package org.geekhub.controller;

import org.geekhub.model.Algorithm;
import org.geekhub.model.CipherOperation;
import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class CipherController {

    @Autowired
    private CipherService service;
    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping("/history")
    public ModelAndView history(ModelAndView modelAndView) {
        modelAndView.addObject("messages", service.getAllHistory());
        modelAndView.setViewName("history");
        return modelAndView;
    }

    @GetMapping("/algorithms")
    public ModelAndView algorithm(ModelAndView modelAndView) {
        modelAndView.addObject("addMessage", new Message());
        return modelAndView;
    }

    @PostMapping("/algorithms/${userId}")
    public ModelAndView postAlgorithm(@ModelAttribute("message") Message message, ModelAndView modelAndView) {
        Message fullMessage = service.saveMessage("originalMessage", Algorithm.CAESAR, CipherOperation.ENCRYPT);
        modelAndView.addObject("message", fullMessage);
        modelAndView.setViewName("algorithm");
        return modelAndView;
    }

}
