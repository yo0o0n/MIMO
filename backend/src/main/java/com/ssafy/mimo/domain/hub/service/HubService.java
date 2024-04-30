package com.ssafy.mimo.domain.hub.service;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubService {
    private final HubRepository hubRepository;
    public String releaseHub() {
        Hub hub = new Hub().builder()
//                .id(null)
//                .house(null)
//                .isRegistered(false)
                .serialNumber(UUID.randomUUID().toString())
//                .registeredDttm(null)
//                .unregisteredDttm(null)
//                .nickname(null)
                .build();
        hubRepository.save(hub);
        return hub.getSerialNumber();
    }
    public Boolean isValidHub(Long hubId) {
        hubRepository.findById(hubId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 허브가 존재하지 않습니다."));
        return true;
    }

    public Long getHubIdBySerialNumber(String serialNumber) {
        Hub hub = hubRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 시리얼 넘버를 가진 허브가 존재하지 않습니다."));
        return hub.getId();
    }
}
