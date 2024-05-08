package com.ssafy.mimo.domain.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketRequestDto {
    @Builder.Default
    private String type = "type";
    @Builder.Default
    private String macAddress = "macAddress";
    @Builder.Default
    private SocketRequestDataDto data = new SocketRequestDataDto();
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"" + type + "Id\":\"" + macAddress +
                "\", \"data\":" + data.toString() +
                '}';
    }
}
