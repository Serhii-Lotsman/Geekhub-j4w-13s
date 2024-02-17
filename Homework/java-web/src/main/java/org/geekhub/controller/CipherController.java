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

    @GetMapping("/algorithm")
    public ModelAndView algorithm(ModelAndView modelAndView) {
        modelAndView.addObject("addMessage", new Message());
        return modelAndView;
    }

    @PostMapping("/algorithm")
    public ModelAndView postAlgorithm(@ModelAttribute("message") Message message, ModelAndView modelAndView) {
        modelAndView.addObject("message", message);
        modelAndView.setViewName("algorithm");
        return modelAndView;
    }

    @PostMapping("/submitCipher")
    public String submitCipher(String inputMessage, String algorithm, String method) {
        Algorithm messageAlgorithm = algorithm.equalsIgnoreCase(Algorithm.CAESAR.name())
            ? Algorithm.CAESAR
            : Algorithm.VIGENERE;
        CipherOperation messageOperation = method.equalsIgnoreCase(CipherOperation.ENCRYPT.name())
            ? CipherOperation.ENCRYPT
            : CipherOperation.DECRYPT;
        Message fullMessage = service.saveMessage(inputMessage, messageAlgorithm, messageOperation);
        postAlgorithm(fullMessage, new ModelAndView());
        return "redirect:/algorithm";
    }

}
