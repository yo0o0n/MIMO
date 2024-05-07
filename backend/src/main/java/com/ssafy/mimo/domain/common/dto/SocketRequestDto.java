package com.ssafy.mimo.domain.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketRequestDto {
    private String type;
    private String deviceSN;
    private SocketRequestDataDto data;
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"" + type + "Id\":\"" + deviceSN +
                "\", \"data\":" + data +
                '}';
    }
}
