package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafy.mimo.socket.global.dto.HubConnectionRequestDto;
import com.ssafy.mimo.socket.global.dto.HubConnectionResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
@RequiredArgsConstructor
public class SocketController {
    private final SocketService socketService;
    private static ConcurrentHashMap<Long, Socket> connections;
    @Getter
    private static ConcurrentHashMap<String, String> receivedMessages;
    private static ConcurrentHashMap<Long, Thread> messageWritterThreads;
    @Getter
    private static ConcurrentHashMap<Long, MessageWriter> messageWritters;
    @Getter
    private static ConcurrentHashMap<Long, List<String>> requestIds;
    public void start(int port) {
        try {
            // Initialize
            ServerSocket serverSocket = new ServerSocket(port);
            connections = new ConcurrentHashMap<>();
            receivedMessages = new ConcurrentHashMap<>();
            messageWritterThreads = new ConcurrentHashMap<>();
            messageWritters = new ConcurrentHashMap<>();
            requestIds = new ConcurrentHashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            // Accept connections from the hub
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                HubConnectionRequestDto request;
                String req = SocketService.readMessage(socket.getInputStream());
                try {
                    request = objectMapper.readValue(req, HubConnectionRequestDto.class);
                } catch (IOException e) { // Error parsing the connection request
                    System.out.printf("Socket Controller: Error parsing the connection request\n%s\n", e.getMessage());
                    socket.close();
                    continue;
                }
                // Hub validation
                if (request.getType().equals("hub") && request.getRequestName().equals("setConnect")) {
                    System.out.printf("Socket Controller: Connection request from hub(%s)\n%s\n", request.getHubSerialNumber(), req);
                    Long hubId = socketService.getHubId(request);
                    if (hubId == null) {
                        System.out.println("Invalid hub ID");
                        socket.close();
                        continue;
                    }
                    System.out.printf("Socket Controller: Connected hub %d\n", hubId);
                    requestIds.put(hubId, new ArrayList<>());
                    // Add the connection to the map
                    connections.put(hubId, socket);
                    // Start the message reader and writter
                    MessageWriter messageWritter = new MessageWriter(hubId, socket, new LinkedBlockingQueue<>());
                    messageWritters.put(hubId, messageWritter);
                    Thread messageReader = new Thread(new MessageReader(hubId, socket, socketService, this));
                    Thread messageWriter = new Thread(messageWritter);
                    messageWritterThreads.put(hubId, messageWriter);
                    messageReader.start();
                    messageWriter.start();
                    // Send the connection response
                    HubConnectionResponseDto response = HubConnectionResponseDto.builder()
                            .type("hub")
                            .requestName("setConnect")
                            .hubId(hubId)
                            .build();
                    socket.getOutputStream().write(objectMapper.writeValueAsString(response).getBytes());
                } else {
                    System.out.println("Socket Controller: Invalid connection request");
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.printf("Socket Controller: Error starting the server socket\n%s\n", e.getMessage());
        }
    }
//    public void stop() {
//        try {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
//            connections.forEach((hubId, socket) -> {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    System.out.println("Error closing the socket: " + e.getMessage());
//                }
//            });
//        } catch (IOException e) {
//            System.out.println("Error closing the server socket: " + e.getMessage());
//        }
//    }
    // 허브 ID에 따라 소켓 반환
    public static Socket getSocket(Long hubId) {
        return connections.get(hubId);
    }
    // Close the connection with the hub
    public static void closeConnection(Long hubId) {
        System.out.println("Removing connection with hub " + hubId);
        // Remove all the data related to the hub
        messageWritters.remove(hubId);
        List<String> idList = requestIds.get(hubId);
        for (String id : idList) {
            receivedMessages.remove(id);
        }
        requestIds.remove(hubId);
        Thread writer = messageWritterThreads.get(hubId);
        if (writer != null) {
            writer.interrupt();
        }
        messageWritterThreads.remove(hubId);
        // Close the connection
        try {
            Socket socket = connections.get(hubId);
            connections.remove(hubId);
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing the connection with hub " + hubId + ": " + e.getMessage());
        }
        System.out.println("Connection with hub " + hubId + " removed");
        // Log the current received messages
        System.out.println("Current received messages:");
        System.out.println(receivedMessages);
    }
    // Get message
    public static synchronized String getMessage(Long hubId, String requestId) throws InterruptedException {
        while (!requestIds.get(hubId).contains(requestId)) {
            // Wait until the message is available
            SocketController.class.wait();
        }
        requestIds.get(hubId).remove(requestId);
        return receivedMessages.remove(requestId);
    }
    // Send message
    public static String sendMessage(Long hubId, String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode messageNode = objectMapper.readValue(message, ObjectNode.class);
            String requestId = UUID.randomUUID().toString();
            messageNode.put("requestId", requestId);
            System.out.printf("SocketController: Sending message to hub %d\n%s\n", hubId, messageNode);
            MessageWriter messageWriter = messageWritters.get(hubId);
            if (messageWriter != null && messageWriter.enqueueMessage(messageNode.toString())) {
                // Add the request ID to the list
                if (requestIds.get(hubId) == null) {
                    List<String> idList = new ArrayList<>();
                    idList.add(requestId);
                    requestIds.put(hubId, idList);
                } else {
                    requestIds.get(hubId).add(requestId);
                }
                System.out.println(receivedMessages);
                return requestId;
            }
            return null;
        } catch (IOException e) {
            System.out.println("Error parsing the message: " + e.getMessage());
            return null;
        }
    }
}
