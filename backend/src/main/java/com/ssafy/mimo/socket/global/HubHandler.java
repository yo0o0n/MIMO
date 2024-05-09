package com.ssafy.mimo.socket.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class HubHandler implements Runnable {
    private Long hubId;
    private Socket socket;
    private final SocketService socketService;
    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String request = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                System.out.println("Received request from hub " + hubId + ": " + request);
                String response = socketService.handleRequest(request);
                socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.out.println("Error processing the request: " + e.getMessage());
        }
    }
}
