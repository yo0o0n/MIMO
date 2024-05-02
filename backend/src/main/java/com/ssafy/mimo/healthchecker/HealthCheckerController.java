package com.ssafy.mimo.healthchecker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class HealthCheckerController {
    @GetMapping
    public String check() {
        return "I'm alive!";
    }
}
