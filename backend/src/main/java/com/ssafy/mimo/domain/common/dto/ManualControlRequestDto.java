package com.ssafy.mimo.domain.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManualControlRequestDto {
    @Builder.Default
    private String type = "type";
    private Long deviceId;
    @Builder.Default
    private SocketRequestDataDto data = new SocketRequestDataDto();
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"" + type + "Id\":\"" + deviceId +
                "\", \"data\":" + data.toString() +
                '}';
    }
}
