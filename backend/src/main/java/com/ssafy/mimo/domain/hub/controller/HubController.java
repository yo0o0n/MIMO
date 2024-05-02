package com.ssafy.mimo.domain.hub.controller;

import com.ssafy.mimo.domain.hub.dto.HubNicknameRequestDto;
import com.ssafy.mimo.domain.hub.dto.HubRegisterRequestDto;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;
    @GetMapping("/new")
    public ResponseEntity<String> releaseNewHub() {
        return ResponseEntity.ok(hubService.releaseHub());
    }
    @GetMapping("/list?houseId={houseId}")
    public ResponseEntity<List<Hub>> getHubs(@RequestParam Long houseId) {
        return ResponseEntity.ok(hubService.getHubs(houseId));
    }
    @PostMapping
    public ResponseEntity<String> registerHub(@RequestBody HubRegisterRequestDto registerNewHubDto) {
        return ResponseEntity.ok(hubService.registerHub(registerNewHubDto.serialNumber(), registerNewHubDto.houseId()));
    }
    @DeleteMapping("/unregister?hubId={hubId}&houseId={houseId}")
    public ResponseEntity<String> unregisterHub(@RequestParam Long hubId,
                                                @RequestParam Long houseId) {
        return ResponseEntity.ok(hubService.unregisterHub(hubId, houseId));
    }
    @PutMapping("/nickname")
    public ResponseEntity<String> updateHubNickname(@RequestBody HubNicknameRequestDto hubNicknameDto) {
        return ResponseEntity.ok(hubService.updateHubNickname(hubNicknameDto.hubId(), hubNicknameDto.nickname()));
    }
}
