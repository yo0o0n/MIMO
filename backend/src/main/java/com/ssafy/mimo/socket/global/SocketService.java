package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.domain.lamp.service.LampService;
import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.domain.light.service.LightService;
import com.ssafy.mimo.domain.window.service.WindowService;
import com.ssafy.mimo.socket.global.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final HubService hubService;
    private final LightService lightService;
    private final LampService lampService;
    private final WindowService windowService;
//    private final CurtainService curtainService;
//    private final ShowerService showerService;
    public Long getHubId(HubConnectionRequestDto hubConnectionRequestDto) {
        try {
            String serialNumber = hubConnectionRequestDto.getHubSerialNumber();
            // 시리얼 넘버로 등록된 허브 ID가 있는지 확인
            Hub hub = hubService.findHubBySerialNumber(serialNumber);
            if (hub != null && hubService.isValidHub(hub)) {  // 등록된 허브인지 확인
                return hub.getId();  // 등록된 시리얼 넘버에 대한 허브 ID 반환
            } else {
                System.out.println("Unregistered or invalid serial number: " + serialNumber);
            }
        } catch (Exception e) {
            System.out.println("Error processing the serial number: " + e.getMessage());
        }
        return null;  // 등록되지 않은 경우나 오류 발생 시 null 반환
    }

    public String handleRequest(String request) {
        String type = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(request);
            type = jsonNode.get("type").asText();
            switch (type) {
                case "hub": // 허브 요청
                    DeviceIdRequestDto deviceIdRequestDto = objectMapper.readValue(request, DeviceIdRequestDto.class);
                    Long deviceId = null;
                    switch (deviceIdRequestDto.requestName()) {
                        case "getLightId":
                            deviceId = lightService.findLightByMacAddress(deviceIdRequestDto.macAddress()).getId();
                        case "getLampId":
                            deviceId = lampService.findLampByMacAddress(deviceIdRequestDto.macAddress()).getId();
                        case "getWindowId":
                            deviceId = windowService.findWindowByMacAddress(deviceIdRequestDto.macAddress()).getId();
                        case "getCurtainId":
                            deviceId = null; // curtainService.findCurtainByMacAddress(deviceIdRequestDto.macAddress()).getId();
                        case "getShowerId":
                            deviceId = null; // showerService.findShowerByMacAddress(deviceIdRequestDto.macAddress()).getId();
                        DeviceIdResponseDto response = DeviceIdResponseDto.builder()
                                .type(deviceIdRequestDto.type())
                                .requestName(deviceIdRequestDto.requestName())
                                .macAddress(deviceIdRequestDto.macAddress())
                                .id(deviceId)
                                .build();
                        return objectMapper.writeValueAsString(response);
                    }
                case "light": // 조명 요청
                    LightControlRequestDto lightRequest = objectMapper.readValue(request, LightControlRequestDto.class);
                    switch (lightRequest.getData().getRequestName()) {
                        case "getCurrentColor":
                            String curColor = lightService.getLightCurColor(lightRequest.getLightId());
                            LightControlResponseDto lightResponse = LightControlResponseDto.builder()
                                    .type(lightRequest.getType())
                                    .lightId(lightRequest.getLightId())
                                    .data(LightControlResponseDataDto.builder()
                                            .requestName(lightRequest.getData().getRequestName())
                                            .curColor(curColor)
                                            .build())
                                    .build();
                            return objectMapper.writeValueAsString(lightResponse);
                        default:
                            return "Invalid request name: " + lightRequest.getData().getRequestName();
                    }
                case "lamp": // 램프 요청
                    LampControlRequestDto lampRequest = objectMapper.readValue(request, LampControlRequestDto.class);
                    switch (lampRequest.getData().getRequestName()) {
                        case "getCurrentColor":
                            String curColor = lampService.getLampCurColor(lampRequest.getLampId());
                            LampControlResponseDto lampResponse = LampControlResponseDto.builder()
                                    .type(lampRequest.getType())
                                    .lampId(lampRequest.getLampId())
                                    .data(LampControlResponseDataDto.builder()
                                            .requestName(lampRequest.getData().getRequestName())
                                            .curColor(curColor)
                                            .build())
                                    .build();
                            return objectMapper.writeValueAsString(lampResponse);
                        default:
                            return "Invalid request name: " + lampRequest.getData().getRequestName();
                    }
                default:
                    return "Invalid request type: " + type;
            }
        } catch (Exception e) {
            System.out.println("Error while processing the request: " + e.getMessage());
            return "Error while processing the request";
        }
    }

    public String readMessage(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }
}
