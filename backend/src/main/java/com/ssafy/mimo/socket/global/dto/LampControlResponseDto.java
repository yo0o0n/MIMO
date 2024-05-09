package com.ssafy.mimo.socket.global.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LampControlResponseDto {
    private String type;
    private Long lampId;
    @Builder.Default
    private LampControlResponseDataDto data = new LampControlResponseDataDto();
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"lampId\":" + lampId +
                ", \"data\":" + data.toString() +
                '}';
    }
}
