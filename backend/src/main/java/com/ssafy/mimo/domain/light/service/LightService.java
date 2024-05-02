package com.ssafy.mimo.domain.light.service;

import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.domain.light.repository.LightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LightService {
    private final LightRepository lightRepository;


    private Light findLightById(Long lightId) {
        return lightRepository.findById(lightId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 조명이 존재하지 않습니다."));
    }
}
