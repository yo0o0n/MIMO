package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mimo.socket.global.dto.HubConnectionRequestDto;
import com.ssafy.mimo.socket.global.dto.HubConnectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private ServerSocket serverSocket;
    private final SocketService socketService;
    private final ApplicationContext applicationContext;
    private static ConcurrentHashMap<Long, Socket> connections;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            connections = new ConcurrentHashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                HubConnectionRequestDto request = null;
                String req = socketService.readMessage(socket.getInputStream());
                try {
                    request = objectMapper.readValue(req, HubConnectionRequestDto.class);
                } catch (IOException e) {
                    System.out.println("Error parsing the connection request: " + e.getMessage());
                    socket.close();
                    continue;
                }
                if (request.getType().equals("hub") && request.getRequestName().equals("setConnect")) {
                    Long hubId = socketService.getHubId(request);
                    System.out.println("Connected hub ID: " + hubId);
                    connections.put(hubId, socket);
                    HubConnectionResponseDto response = HubConnectionResponseDto.builder()
                            .type("hub")
                            .requestName("setConnect")
                            .hubId(hubId)
                            .build();
                    socket.getOutputStream().write(objectMapper.writeValueAsString(response).getBytes());
                    applicationContext.getBean(HubHandler.class).start(socket, hubId);
                } else {
                    System.out.println("Invalid connection request");
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting the server socket: " + e.getMessage());
        }
    }
    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            connections.forEach((hubId, socket) -> {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing the socket: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Error closing the server socket: " + e.getMessage());
        }
    }
    // 허브 ID에 따라 소켓 반환
    public Socket getSocket(Long hubId) {
        return connections.get(hubId);
    }
}
