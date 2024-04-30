package com.ssafy.mimo.socket.global;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private ServerSocket serverSocket;
    private final ApplicationContext applicationContext;
    private final HubService hubService;
    private static ConcurrentHashMap<Long, Socket> connections;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Long hubId = getHubId(socket);
                if (hubId != null) {
                    connections.put(hubId, socket); // 유효한 허브 ID에 대해서만 소켓 저장
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getHubId(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serialNumber = reader.readLine();  // 클라이언트로부터 시리얼 넘버 읽기
            if (serialNumber != null && !serialNumber.isEmpty()) {
                // 시리얼 넘버로 등록된 허브 ID가 있는지 확인
                Hub hub = hubService.findBySerialNumber(serialNumber);
                if (hub != null && hubService.isValidHub(hub)) {  // 등록된 허브인지 확인
                    return hub.getId();  // 등록된 시리얼 넘버에 대한 허브 ID 반환
                } else {
                    System.out.println("Unregistered or invalid serial number: " + serialNumber);
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing the serial number: " + e.getMessage());
        }
        return null;  // 등록되지 않은 경우나 오류 발생 시 null 반환
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
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 허브 ID에 따라 소켓 반환
    public Socket getSocket(Long hubId) {
        return connections.get(hubId);
    }
}
