package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

@Getter
@AllArgsConstructor
public class MessageWriter implements Runnable {
    private Long hubId;
    private Socket socket;
    private BlockingQueue<String> messageQueue;
    @Override
    public void run() {
        System.out.printf("MessageWritter %d started\n", hubId);
        while (!socket.isClosed()) {
            try {
                // Take the message from the queue
                String message = messageQueue.take();
                System.out.printf("Sending message to hub %d: %s\n", hubId, message);
                socket.getOutputStream().write(message.getBytes());
            } catch (InterruptedException e) {
                // Error while taking the message
                System.out.printf("Connection with hub %d is closed\n", hubId);
                break;
            } catch (Exception e) {
                // Error while writing the message
                System.out.printf("Error while writing the message to hub %d: %s\n", hubId, e.getMessage());
                break;
            }
        }
        System.out.printf("MessageWritter %d stopped\n", hubId);
    }
    // Send a message to the hub through the message queue
    public boolean enqueueMessage(String message) {
        return messageQueue.offer(message);
    }
}
