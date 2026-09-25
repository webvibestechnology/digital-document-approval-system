package com.documentapproval;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "<h1>My Spring Boot project has started successfully!</h1>";
    }
}
