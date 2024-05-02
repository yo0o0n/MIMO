package com.ssafy.mimo.domain.light.service;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.domain.hub.service.UserService;
import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.domain.light.repository.LightRepository;
import com.ssafy.mimo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LightService {
    private final LightRepository lightRepository;
    private final UserService userService;
    private final HubService hubService;
    private Light findLightById(Long lightId) {
        return lightRepository.findById(lightId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 조명이 존재하지 않습니다."));
    }
    private String registerLight(Long lightId, Long userId, Long hubId, String nickname) {
        Light light = findLightById(lightId);
        if (light.isRegistered()) {
            return "Light already registered!";
        }
        User user = userService.findUserById(userId);
        Hub hub = hubService.findHubById(hubId);
        try {
            light.setUser(user);
            light.setHub(hub);
            light.setRegistered(true);
            light.setRegisteredDttm(LocalDateTime.now());
            light.setNickname(nickname);
            lightRepository.save(light);
        } catch (Exception e) {
            return "Failed to register light!";
        }
        return "Light registered!";
    }
    public String unregisterLight(Long lightId) {
        Light light = findLightById(lightId);
        if (!light.isRegistered()) {
            return "Light already unregistered!";
        }
        try {
            light.setUser(null);
            light.setHub(null);
            light.setRegistered(false);
            light.setUnregisteredDttm(LocalDateTime.now());
            light.setNickname(null);
            lightRepository.save(light);
        } catch (Exception e) {
            return "Failed to unregister light!";
        }
        return "Light unregistered!";
    }
}
