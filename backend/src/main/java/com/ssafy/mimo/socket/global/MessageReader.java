package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public class MessageReader implements Runnable {
    private Long hubId;
    private Socket socket;
    private final SocketService socketService;
    private final SocketController socketController;
    @Override
    public void run() {
        System.out.printf("MessageReader %d: Started\n", hubId);
        // Initialize
        ConcurrentHashMap<String, String> messages = SocketController.getReceivedMessages();
        // Read message from the hub until the connection is closed
        while (!socket.isClosed()) {
            String message;
            JsonNode json_message;
            try { // Read the message
                message = SocketService.readMessage(socket.getInputStream());
                json_message = parseMessage(message);
            } catch (Exception e) { // Error while reading the message
//                System.out.printf("MessageReader %d: Error while reading the message\n%s\n", hubId, e.getMessage());
                SocketController.closeConnection(hubId); // Clean up
                break;
            }
            System.out.printf("MessageReader %d: Received message\n%s\n", hubId, message);
            if (isRequest(json_message)) { // Hub의 요청인 경우 응답 반환
                ObjectNode response = socketService.handleRequest(message);
                if (response == null) continue;
                try { // Send the response
                    socket.getOutputStream().write(response.toString().getBytes());
                    System.out.printf("MessageReader %d: Sent response\n%s\n", hubId, response);
                } catch (Exception e) { // Error while sending the response
                    System.out.printf("MessageReader %d: Error while sending the response\n%s\n", hubId, e.getMessage());
                    SocketController.closeConnection(hubId); // Clean up
                    break;
                }
            } else { // Hub의 응답인 경우 메시지 맵에 저장
                System.out.printf("MessageReader %d: Put message in the queue\n", hubId);
                String requestId = getRequestId(json_message);
                SocketController.getRequestIds().get(hubId).add(requestId);
                // 메시지에서 requestId 제거 후 저장
                ObjectNode messageNode = (ObjectNode) json_message;
                messageNode.remove("requestId");
                messages.put(requestId, messageNode.toString());
                SocketController.getFutureReceivedMessages().get(requestId).complete(messageNode.toString());
                // Log messages
                System.out.printf("MessageReader: Current receivedMessages:\n%s\n", SocketController.getReceivedMessages());
            }
        }
        System.out.printf("MessageReader %d: Stopped\n", hubId);
    }
    private static boolean isRequest(JsonNode jsonNode) {
        return !jsonNode.has("requestId");
    }
    private static JsonNode parseMessage(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(message);
    }
    private static String getRequestId(JsonNode request) {
        return request.get("requestId").asText();
    }
}
