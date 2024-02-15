package org.geekhub;

import org.geekhub.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @GetMapping("/")
    public List<Message> index() {
        return List.of(new Message(
            12,
            "First message on ui",
            "Encrypted message",
            "caesar",
            "encrypt",
            "15-02-2024 14:00:00",
            "success"
        ));
    }

}
