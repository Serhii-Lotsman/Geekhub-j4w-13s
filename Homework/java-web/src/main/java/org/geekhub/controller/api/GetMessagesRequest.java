package org.geekhub.controller.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3/history")
@Tag(name="get-history-controller", description = "Get all history,by message id, by algorithm, by date or statistics. By default get all history")
public class GetMessagesRequest {

    private final CipherService cipherService;

    public GetMessagesRequest(CipherService cipherService) {
        this.cipherService = cipherService;
    }

    @GetMapping
    public List<Message> allHistory() {
        return cipherService.getAllMessages();
    }

    @GetMapping("/algorithms")
    public List<Message> history(@RequestParam(defaultValue = "") String param) {
        return cipherService.getMessagesByAlgorithm(param);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get message by id successfully"),
        @ApiResponse(responseCode = "500", description = "This id doesn't exist")
    })
    public Message messageById(@PathVariable long id) {
        return cipherService.getMessageById(id);
    }

    @GetMapping("/date-range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get message by date successfully"),
        @ApiResponse(responseCode = "500", description = "Such date doesn't exist or invalid. Try 'yyyy-MM-dd HH:mm:ss'")
    })
    public List<Message> messagesByDate(String dateFrom, String dateTo) {
        return cipherService.getMessageByDate(dateFrom, dateTo);
    }

    @GetMapping("/statistics")
    public List<Map<String, Long>> messageStatistic() {
        return cipherService.getStatistics();
    }

}
