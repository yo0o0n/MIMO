package com.ssafy.mimo.domain.hub.controller;

import com.ssafy.mimo.domain.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;
    @GetMapping("/new")
    public ResponseEntity<String> releaseNewHub() {
        return ResponseEntity.ok(hubService.releaseHub());
    }
}
