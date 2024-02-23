package org.geekhub.controller;

import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmController {

    @Autowired
    private CipherService service;

    @GetMapping
    public ModelAndView algorithm(ModelAndView modelAndView) {
        modelAndView.addAllObjects(Map.of(
            "addMessage", new Message(),
            "activeButton", "algorithms"
        ));
        return modelAndView;
    }

    @PostMapping
    public ModelAndView submitCipher(String inputMessage,
                                     String algorithm,
                                     String operation,
                                     ModelAndView modelAndView) {
        Message fullMessage = service.saveMessage(inputMessage, algorithm, operation);
        modelAndView.addObject("message", fullMessage);
        modelAndView.setViewName("algorithms");
        return modelAndView;
    }
}