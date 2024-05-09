package com.ssafy.mimo.domain.common.controller;

import com.ssafy.mimo.domain.common.dto.ManualControlRequestDto;
import com.ssafy.mimo.domain.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/control")
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;
    @PostMapping
    public ResponseEntity<String> controlDevice(@RequestBody ManualControlRequestDto manualControlRequestDto) {
        return ResponseEntity.ok(commonService.controlDevice(manualControlRequestDto));
    }
}
