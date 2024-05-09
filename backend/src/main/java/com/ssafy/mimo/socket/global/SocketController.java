package com.ssafy.mimo.socket.global;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
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
    private final HubService hubService;
    private static ConcurrentHashMap<Long, Socket> connections;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            connections = new ConcurrentHashMap<>();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Long hubId = socketService.getHubId(socket);
                if (hubId != null) {
                    System.out.println("Hub connected: " + hubId);
                    connections.put(hubId, socket); // 유효한 허브 ID에 대해서만 소켓 저장
                    // start hub request handler thread
                    HubHandler hubHandler = applicationContext.getBean(HubHandler.class);
                    hubHandler.setHubId(hubId);
                    hubHandler.setSocket(socket);
                    new Thread(hubHandler).start();
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
