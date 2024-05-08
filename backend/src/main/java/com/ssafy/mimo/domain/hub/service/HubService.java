package com.ssafy.mimo.domain.hub.service;

import com.ssafy.mimo.domain.house.service.HouseService;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.house.entity.House;
import com.ssafy.mimo.domain.hub.repository.HubRepository;
import com.ssafy.mimo.domain.lamp.service.LampService;
import com.ssafy.mimo.domain.light.service.LightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubService {
    private final HubRepository hubRepository;
    private final HouseService houseService;
//    private final LightService lightService;
//    private final LampService lampService;
//    private final CurtainService curtainService;
//    private final WindowService windowService;
//    private final ShowerService showerService;
    public String releaseHub() {
        Hub hub = Hub.builder()
                .serialNumber(UUID.randomUUID().toString())
                .nickname("새로운 허브")
                .build();
        hub = hubRepository.save(hub);
        return hub.getSerialNumber();
    }
    public List<Hub> getHubs(Long houseId) {
        return hubRepository.findByHouseId(houseId);
    }
    public String registerHub(String serialNumber, Long houseId) {
        Hub hub = findHubBySerialNumber(serialNumber);
        House house = houseService.findHouseById(houseId);
        if (!hub.isRegistered()){
            hub.setRegistered(true);
            hub.setRegisteredDttm(LocalDateTime.now());
            hub.setHouse(house);
            hubRepository.save(hub);
            return "허브 등록 성공";
        } else {
            throw new IllegalArgumentException("이미 등록된 허브입니다.");
        }
    }
    public String unregisterHub(Long hubId, Long houseId) {
        Hub hub = findHubById(hubId);
        House house = houseService.findHouseById(houseId);
        if (hub.isRegistered() && hub.getHouse().getId().equals(houseId)){
            hub.setRegistered(false);
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
        return hub.isRegistered();
    }
    public Hub findHubBySerialNumber(String serialNumber) {
        return hubRepository.findBySerialNumber(serialNumber)
                .orElse(null);
    }
    public Hub findHubById(Long hubId) {
        return hubRepository.findById(hubId)
                .orElse(null);
    }
}
