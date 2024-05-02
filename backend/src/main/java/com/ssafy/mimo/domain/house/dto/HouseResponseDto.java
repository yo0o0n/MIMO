package com.ssafy.mimo.domain.house.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class HouseResponseDto {

    private final Long id;
    private final String nickname;
    private final String address;
    private final boolean isHome;
    private final List<String> devices;

}
