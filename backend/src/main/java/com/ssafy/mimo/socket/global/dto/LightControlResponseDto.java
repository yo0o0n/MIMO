package com.ssafy.mimo.socket.global.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LightControlResponseDto {
    private String type;
    private Integer lightId;
    @Builder.Default
    private LightControlResponseDataDto data = new LightControlResponseDataDto();
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"lightId\":" + lightId +
                ", \"data\":" + data.toString() +
                '}';
    }
}
