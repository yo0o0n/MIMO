package com.ssafy.mimo.domain.light.dto;

public record LightRegisterRequestDto(
        Long lightId,
        Long hubId,
        Long userId
) {
}
