package com.ssafy.mimo.domain.hub.service;

import com.ssafy.mimo.domain.house.service.HouseService;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.house.entity.House;
import com.ssafy.mimo.domain.hub.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubService {
    private final HubRepository hubRepository;
    public String releaseHub() {
        Hub hub = Hub.builder()
                .serialNumber(UUID.randomUUID().toString())
                .build();
        hub = hubRepository.save(hub);
        return hub.getSerialNumber();
    }
    public String registerHub(String serialNumber, Long houseId) {
        Hub hub = hubRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 시리얼 넘버를 가진 허브가 존재하지 않습니다."));
        House house = houseRepository.findById()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 집이 존재하지 않습니다."));
        if (!hub.getIsRegistered()){
            hub.setIsRegistered(true);
            hub.setRegisteredDttm(LocalDateTime.now());
            hub.setHouse(house);
            hubRepository.save(hub);
            return "허브 등록 성공";
        } else {
            throw new IllegalArgumentException("이미 등록된 허브입니다.");
        }
    }
    public String unregisterHub(String serialNumber, Long houseId) {
        Hub hub = hubRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 시리얼 넘버를 가진 허브가 존재하지 않습니다."));
        House house = houseRepository.findById()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 집이 존재하지 않습니다."));
        if (hub.getIsRegistered() && hub.getHouse().getId().equals(houseId)){
            hub.setIsRegistered(false);
            hub.setHouse(null);
            hubRepository.save(hub);
            return "허브 등록 해제 성공";
        } else {
            throw new IllegalArgumentException("등록되지 않은 허브이거나 해당 집에 등록되지 않은 허브입니다.");
        }
    }
    public String updateHubNickname(Long hubId, String nickname) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 허브가 존재하지 않습니다."));
        hub.setNickname(nickname);
        hubRepository.save(hub);
        return "허브 닉네임 변경 성공";
    }
    public Boolean isValidHub(Hub hub) {
        hubRepository.findById(hub.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 허브가 존재하지 않습니다."));
        return true;
    }

    public Hub findBySerialNumber(String serialNumber) {
        return hubRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 시리얼 넘버를 가진 허브가 존재하지 않습니다."));
    }
}
