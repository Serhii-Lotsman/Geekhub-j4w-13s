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
        List<Message> messages = cipherService.getMessagesByDateAndAlgorithm(param, dateFrom, dateTo);
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("history");
        return modelAndView;
    }

}
