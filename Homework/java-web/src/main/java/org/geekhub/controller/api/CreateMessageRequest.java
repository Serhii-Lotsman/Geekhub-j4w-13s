package org.geekhub.controller.api;

import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/algorithms")
public class CreateMessageRequest {

    @Autowired
    private CipherService service;

    @PostMapping
    public Message createMessage(
        long userId,
        String inputMessage,
        String algorithm,
        String operation
    ) {
        return service.saveMessage(userId, inputMessage, algorithm, operation);
    }
}
