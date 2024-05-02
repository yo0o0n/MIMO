package com.ssafy.mimo.domain.light.controller;

import com.ssafy.mimo.domain.light.dto.LightControlRequestDto;
import com.ssafy.mimo.domain.light.dto.LightRegisterRequestDto;
import com.ssafy.mimo.domain.light.service.LightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/light")
public class LightController {
    private final LightService lightService;
    @PostMapping("/register")
    public ResponseEntity<String> registerLight(@RequestBody LightRegisterRequestDto lightRegisterRequestDto) {
        return ResponseEntity.ok("register light");
    }
    @DeleteMapping("/unregister")
    public ResponseEntity<String> unregisterLight(@RequestBody LightRegisterRequestDto lightRegisterRequestDto) {
        return ResponseEntity.ok("unregister light");
    }
    @PostMapping
    public ResponseEntity<String> controlLight(@RequestBody LightControlRequestDto lightControlRequestDto) {
        return ResponseEntity.ok(lightService.controlLight(lightControlRequestDto));
    }
}
