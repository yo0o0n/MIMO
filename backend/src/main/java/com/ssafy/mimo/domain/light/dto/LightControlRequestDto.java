package com.ssafy.mimo.domain.light.dto;

import lombok.Builder;

@Builder
public record LightControlRequestDto(
        Long lightId,
        String requestType,
        String requestName
) {}
