package org.geekhub.controller;

import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private CipherService cipherService;

    @GetMapping
    public ModelAndView history(ModelAndView modelAndView,
                                @RequestParam(defaultValue = "") String param,
                                String dateFrom,
                                String dateTo) {
        List<Message> messages;
        if (dateFrom != null && dateTo != null) {
            messages = cipherService.getMessageByDate(dateFrom, dateTo);
        } else {
            switch (param) {
                case "caesar" -> messages = cipherService.getMessageByAlgorithm("CAESAR");
                case "vigenere" -> messages = cipherService.getMessageByAlgorithm("VIGENERE");
                default -> messages = cipherService.getAllHistory();
            }
        }
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("history");
        return modelAndView;
    }

}
