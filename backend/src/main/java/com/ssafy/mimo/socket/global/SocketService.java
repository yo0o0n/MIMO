package com.ssafy.mimo.socket.global;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final HubService hubService;
    private static final int serialNumberLength = 16;
    @NotNull
    public String getSerialNumber(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] serialNumberBytes = new byte[serialNumberLength];
        int totalBytesRead = 0;
        int bytesRead = 0;
        while (totalBytesRead < serialNumberLength
                && (bytesRead = inputStream.read(serialNumberBytes, totalBytesRead, serialNumberLength - totalBytesRead)) != -1) {
            totalBytesRead += bytesRead;
        }
        return new String(serialNumberBytes);
    }
    public Long getHubId(Socket socket) {
        try {
            String serialNumber = getSerialNumber(socket);
            // 시리얼 넘버로 등록된 허브 ID가 있는지 확인
            Hub hub = hubService.findHubBySerialNumber(serialNumber);
            if (hub != null && hubService.isValidHub(hub)) {  // 등록된 허브인지 확인
                return hub.getId();  // 등록된 시리얼 넘버에 대한 허브 ID 반환
            } else {
                System.out.println("Unregistered or invalid serial number: " + serialNumber);
            }
        } catch (IOException e) {
            System.out.println("Error processing the serial number: " + e.getMessage());
        }
        return null;  // 등록되지 않은 경우나 오류 발생 시 null 반환
    }
    public String handleRequest(String request) {
        return "Received request: " + request;
    }
}
