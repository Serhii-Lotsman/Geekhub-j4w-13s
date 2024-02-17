package org.geekhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CipherController {
    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

}
