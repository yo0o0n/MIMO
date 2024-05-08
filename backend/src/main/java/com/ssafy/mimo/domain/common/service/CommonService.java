package com.ssafy.mimo.domain.common.service;

import com.ssafy.mimo.common.BaseDeviceEntity;
import com.ssafy.mimo.domain.common.dto.ManualControlRequestDto;
import com.ssafy.mimo.domain.common.dto.SocketRequestDto;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.domain.light.service.LightService;
import com.ssafy.mimo.socket.global.SocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final HubService hubService;
    private final LightService lightService;
//    private final CurtainService curtainService;
//    private final LampService lampService;
//    private final WindowService windowService;
    private final SocketController socketController;
    public String controlDevice(ManualControlRequestDto manualControlRequestDto) {
        BaseDeviceEntity device = null;
        switch (manualControlRequestDto.getType()) {
            case "light":
                device = lightService.findLightById(manualControlRequestDto.getDeviceId());
                break;
            case "lamp":
//                device = lampService.findLampById(manualControlRequestDto.getDeviceId());
//                break;
            case "curtain":
//                device = curtainService.findCurtainById(manualControlRequestDto.getDeviceId());
//                break;
            case "window":
//                device = windowService.findWindowById(manualControlRequestDto.getDeviceId());
//                break;
            case "shower":
//                device = showerService.findShowerById(manualControlRequestDto.getDeviceId());
//                break;
            default:
                return "Invalid device type";
        }
        Hub hub = device.getHub();
        if (hub == null)
            return "Device is not connected to a hub";
//        SocketRequestDto request = SocketRequestDto.builder()
//                .type(manualControlRequestDto.getType())
//                .macAddress(device.getMacAddress())
//                .data(manualControlRequestDto.getData())
//                .build();
        try (Socket hub_connection = socketController.getSocket(hub.getId())) {
            hub_connection.getOutputStream().write(manualControlRequestDto.toString().getBytes());
        } catch (Exception e) {
//            return "Failed to send request to hub";
            return manualControlRequestDto.toString();
        }
        return manualControlRequestDto.toString();
    }
}
