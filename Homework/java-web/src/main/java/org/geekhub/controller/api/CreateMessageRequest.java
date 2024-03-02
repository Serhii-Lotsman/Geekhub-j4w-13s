package org.geekhub.controller.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/algorithms")
@Tag(name="create-message-controller", description = "Create message with available algorithms: caesar/vigenere, operations: encrypt/decrypt")
public class CreateMessageRequest {

    private final CipherService cipherService;

    public CreateMessageRequest(CipherService cipherService) {
        this.cipherService = cipherService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message created successfully"),
        @ApiResponse(responseCode = "500", description = "This userId doesn't exist")
    })
    @PostMapping
    public Message createMessage(
        long userId,
        String inputMessage,
        String algorithm,
        String operation
    ) {
        return cipherService.saveMessage(userId, inputMessage, algorithm, operation);
    }
}
