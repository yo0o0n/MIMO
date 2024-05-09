package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.socket.global.dto.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final HubService hubService;
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
        RequestMappingDto requestMappingDto = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            requestMappingDto = objectMapper.readValue(request, RequestMappingDto.class);
            String type = requestMappingDto.type();
            switch (type) {
                case "hub": // 허브 요청
                    DeviceIdRequestDto deviceIdRequestDto = objectMapper.readValue(request, DeviceIdRequestDto.class);
                    // TODO: Find device id by mac address
                    DeviceIdResponseDto response = DeviceIdResponseDto.builder()
                            .type(deviceIdRequestDto.type())
                            .requestName(deviceIdRequestDto.requestName())
                            .macAddress(deviceIdRequestDto.macAddress())
                            .id(123123L)
                            .build();
                    return objectMapper.writeValueAsString(response);
                case "light": // 조명 요청
                    // TODO: Handle light request
                case "lamp": // 램프 요청
                    // TODO: Handle lamp request
                default:
                    DeviceControlDto deviceControlDto = objectMapper.readValue(request, DeviceControlDto.class);
                    return deviceControlDto.toString();
            }
        } catch (IOException e) {
            return "Error while parsing the request: " + e.getMessage();
        }
    }
    public String readMessage(@NotNull InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }
}
