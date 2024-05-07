package com.ssafy.mimo.domain.house.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class HouseUpdateRequestDto {

    private final String nickname;

}
