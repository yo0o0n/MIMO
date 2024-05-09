package com.ssafy.mimo.socket.global.dto;

import lombok.Builder;

@Builder
public record DeviceIdResponseDto(
        String type,
        String requestName,
        String macAddress,
        Long id
) {
}
