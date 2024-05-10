package com.ssafy.mimo.user.controller;

import com.ssafy.mimo.user.dto.MyInfoResponseDto;
import com.ssafy.mimo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 컨트롤러", description = "사용자 정보 일치 기능이 포함되어 있음")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "해당 사용자의 집/허브 유무 파악")
    @GetMapping("/myInfo")
    public ResponseEntity<MyInfoResponseDto> getHomeAndHubInfo(@RequestHeader("X-AUTH-TOKEN") String token) {
        Long userId = userService.getUserId(token);
        MyInfoResponseDto info = userService.getHomeAndHubInfo(userId);
        return ResponseEntity.ok(info);
    }
}
