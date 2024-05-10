package com.ssafy.mimo.domain.house.dto;

import lombok.*;

@Builder
public record DeviceDetailDto(
        Long userId,
        Long hubId,
        Long deviceId,
        String type,
        String nickname,
        boolean isAccessible) {

}
