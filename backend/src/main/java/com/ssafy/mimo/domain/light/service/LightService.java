package com.ssafy.mimo.domain.light.service;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.domain.light.dto.LightControlRequestDto;
import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.domain.light.repository.LightRepository;
import com.ssafy.mimo.socket.global.SocketController;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Socket;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LightService {
    private final LightRepository lightRepository;
    private final UserService userService;
    private final HubService hubService;
    private final SocketController socketController;
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
    private String updateLightNickname(Long lightId, String nickname) {
        Light light = findLightById(lightId);
        try {
            light.setNickname(nickname);
            lightRepository.save(light);
        } catch (Exception e) {
            return "Failed to update light nickname!";
        }
        return "Light nickname updated!";
    }
    public String controlLight(LightControlRequestDto lightControlRequestDto) {
        Light light = findLightById(lightControlRequestDto.lightId());
        Hub hub = light.getHub();
        if (hub != null) {
            try (Socket socket = socketController.getSocket(hub.getId())) {
                String message = "{\"type\":" + lightControlRequestDto.requestType() + ",\"requestName\":\"" + lightControlRequestDto.requestName() + "\"}";
                socket.getOutputStream().write(message.getBytes());
                return "Light turned off!";
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to connect to hub!");
            }
        }
        throw new IllegalArgumentException("Light is not connected to a hub!");
    }
    public Light findLightByMacAddress(String macAddress) {
        return lightRepository.findByMacAddress(macAddress)
                .orElseThrow(() -> new IllegalArgumentException("해당 MAC 주소를 가진 조명이 존재하지 않습니다."));
    }
}
