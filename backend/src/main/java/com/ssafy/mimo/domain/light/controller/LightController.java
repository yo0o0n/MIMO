package com.ssafy.mimo.domain.light.controller;

import com.ssafy.mimo.domain.light.dto.LightControlRequestDto;
import com.ssafy.mimo.domain.light.dto.LightRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/light")
public class LightController {
    @PostMapping("/register")
    public ResponseEntity<String> registerLight(@RequestBody LightRegisterRequestDto lightRegisterRequestDto) {
        return ResponseEntity.ok("register light");
    }
    @DeleteMapping("/unregister")
    public ResponseEntity<String> unregisterLight(@RequestBody LightRegisterRequestDto lightRegisterRequestDto) {
        return ResponseEntity.ok("unregister light");
    }
    @PostMapping
    public ResponseEntity<String> turnOnLight(@RequestBody LightControlRequestDto lightControlRequestDto) {
        return ResponseEntity.ok("turn on light");
    }
}
