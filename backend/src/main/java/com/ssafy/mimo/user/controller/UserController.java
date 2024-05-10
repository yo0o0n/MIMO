package com.ssafy.mimo.user.controller;

import com.ssafy.mimo.user.dto.MyInfoResponseDto;
import com.ssafy.mimo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/myInfo")
    public ResponseEntity<MyInfoResponseDto> getHomeAndHubInfo(@RequestHeader("X-AUTH-TOKEN") String token) {
        Long userId = userService.getUserId(token);
        MyInfoResponseDto info = userService.getHomeAndHubInfo(userId);
        return ResponseEntity.ok(info);
    }
}
