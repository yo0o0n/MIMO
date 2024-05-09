package com.ssafy.mimo.socket.global.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceControlDto {
    private String type;
    private Integer deviceId;
    @Builder.Default
    private DeviceControlDataDto data = new DeviceControlDataDto();
    @Override
    public String toString() {
        return "{\"type\":\"" + type +
                "\", \"" + type + "Id\":" + deviceId +
                ", \"data\":" + data.toString() +
                '}';
    }
}
