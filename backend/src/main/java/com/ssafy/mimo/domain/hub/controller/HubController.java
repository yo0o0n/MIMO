package com.ssafy.mimo.domain.hub.controller;

import com.ssafy.mimo.domain.hub.dto.HubNicknameDto;
import com.ssafy.mimo.domain.hub.dto.RegisterHubDto;
import com.ssafy.mimo.domain.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;
    @GetMapping("/new")
    public ResponseEntity<String> releaseNewHub() {
        return ResponseEntity.ok(hubService.releaseHub());
    }
    @PostMapping
    public ResponseEntity<String> registerHub(@RequestBody RegisterHubDto registerNewHubDto) {
        return ResponseEntity.ok(hubService.registerHub(registerNewHubDto.serialNumber(), registerNewHubDto.houseId()));
    }
    @DeleteMapping("unregister?=serialNumber={serialNumber}&houseId={houseId}")
    public ResponseEntity<String> unregisterHub(@RequestParam String serialNumber,
                                                @RequestParam Long houseId) {
        return ResponseEntity.ok(hubService.unregisterHub(serialNumber, houseId));
    }
    @PutMapping("/nickname")
    public ResponseEntity<String> updateHubNickname(@RequestBody HubNicknameDto hubNicknameDto) {
        return ResponseEntity.ok(hubService.updateHubNickname(hubNicknameDto.hubId(), hubNicknameDto.nickname()));
    }
}
