package com.ssafy.mimo.domain.hub.service;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
