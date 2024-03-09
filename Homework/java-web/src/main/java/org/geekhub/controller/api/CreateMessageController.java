package org.geekhub.controller.api;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.model.Message;
import org.geekhub.service.CipherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
    info = @Info(
        title = "Cipher API",
        version = "1.0",
        description = "Use this API for managing history, editing users or messages",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Cipher-Maker",
            url = "localhost:8088"
        )
    )
)
@RestController
@RequestMapping("/api/v1/algorithms")
@Tag(name="create-message-controller", description = "Create message with available algorithms: caesar/vigenere, operations: encrypt/decrypt")
public class CreateMessageController {

    private final CipherService cipherService;

    public CreateMessageController(CipherService cipherService) {
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
