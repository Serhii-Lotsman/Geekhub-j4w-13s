package org.geekhub.controller.api;

import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/history")
public class GetMessagesRequest {

    @Autowired
    private CipherService cipherService;

    @GetMapping
    public List<Message> allHistory() {
        return cipherService.getAllMessages();
    }
    @GetMapping("/algorithms")
    public List<Message> history(@RequestParam(defaultValue = "") String param) {
        return cipherService.getMessagesByAlgorithm(param);
    }

    @GetMapping("/{id}")
    public Message messageById(@PathVariable long id) {
        return cipherService.getMessageById(id);
    }

    @GetMapping("/date-range")
    public List<Message> messagesByDate(String dateFrom, String dateTo) {
        return cipherService.getMessageByDate(dateFrom, dateTo);
    }

    @GetMapping("/statistics")
    public List<Map<String, Long>> messageStatistic() {
        var countOfUsage = cipherService.getCountOfUsage();
        var uniqueMessages = cipherService.getUniqueMessages();
        List<Map<String, Long>> resultList = new ArrayList<>();
        resultList.add(new HashMap<>(countOfUsage));
        resultList.add(new HashMap<>(uniqueMessages));
        return resultList;
    }

}
