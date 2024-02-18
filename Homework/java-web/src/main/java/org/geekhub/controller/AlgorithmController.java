package org.geekhub.controller;

import org.geekhub.model.Algorithm;
import org.geekhub.model.CipherOperation;
import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmController {

    @Autowired
    private CipherService service;

    @GetMapping
    public ModelAndView algorithm(ModelAndView modelAndView) {
        modelAndView.addObject("addMessage", new Message());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView submitCipher(String inputMessage,
                                     String algorithm,
                                     String method,
                                     ModelAndView modelAndView) {
        Algorithm messageAlgorithm = algorithm.equalsIgnoreCase(Algorithm.CAESAR.name())
            ? Algorithm.CAESAR
            : Algorithm.VIGENERE;
        CipherOperation messageOperation = method.equalsIgnoreCase(CipherOperation.ENCRYPT.name())
            ? CipherOperation.ENCRYPT
            : CipherOperation.DECRYPT;
        Message fullMessage = service.saveMessage(inputMessage, messageAlgorithm, messageOperation);

        modelAndView.setViewName("algorithms");
        modelAndView.addObject("message", fullMessage);
        return modelAndView;
    }
}
