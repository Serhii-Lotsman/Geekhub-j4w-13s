package org.geekhub.controller;

import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final CipherService cipherService;

    public HistoryController(CipherService cipherService) {
        this.cipherService = cipherService;
    }

    @GetMapping
    public ModelAndView history(ModelAndView modelAndView, @RequestParam(defaultValue = "") String param) {
        List<Message> messages = cipherService.getMessagesByAlgorithm(param);
        modelAndView.addAllObjects(Map.of(
            "messages", messages,
            "activeButton", "history"
        ));
        modelAndView.setViewName("history");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView messageById(ModelAndView modelAndView, @PathVariable long id) {
        Message message = cipherService.getMessageById(id);
        modelAndView.setViewName("messageById");
        modelAndView.addAllObjects(Map.of(
            "message", message,
            "activeButton", "history"
        ));
        return modelAndView;
    }

    @GetMapping("/date-range")
    public ModelAndView messagesByDate(ModelAndView modelAndView, String dateFrom, String dateTo) {
        List<Message> messages = cipherService.getMessageByDate(dateFrom, dateTo);
        modelAndView.setViewName("date-range");
        modelAndView.addAllObjects(Map.of(
            "messages", messages,
            "activeButton", "history"
            ));
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }

    @GetMapping("/statistics")
    public ModelAndView messageStatistic(ModelAndView modelAndView) {
        var statistics = cipherService.getStatistics();
        modelAndView.setViewName("statistics");
        modelAndView.addAllObjects(Map.of(
            "statistics", statistics,
            "activeButton", "history"
        ));
        return modelAndView;
    }

}
