package com.ssafy.mimo.domain.house.dto;

import lombok.Builder;

@Builder
public record HouseRegisterRequestDto(
        String serialNumber,
        String address,
        String nickname) {

}
