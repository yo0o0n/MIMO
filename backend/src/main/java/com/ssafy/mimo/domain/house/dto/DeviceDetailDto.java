package com.ssafy.mimo.domain.house.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class DeviceDetailDto {

    private Long deviceId;
    private String nickname;
    private String type;
    private boolean isAccessible;

    public void determineType(String idType) {
        switch (idType) {
            case "lightId":
                this.type = "Light";
                break;
            case "lampId":
                this.type = "Lamp";
                break;
            case "curtainId":
                this.type = "Curtain";
                break;
            case "windowId":
                this.type = "Window";
                break;
            default:
                this.type = "Unknown";
                break;
        }
    }
}
