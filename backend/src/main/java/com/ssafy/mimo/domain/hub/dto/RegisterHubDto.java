package com.ssafy.mimo.domain.hub.dto;

import lombok.*;

@Builder
public record RegisterHubDto(
        String serialNumber,
        Long houseId
) {}
