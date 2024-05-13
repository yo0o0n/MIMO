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
        System.out.printf("MessageWriter %d started\n", hubId);
        while (!socket.isClosed()) {
            try {
                // Take the message from the queue
                String message = messageQueue.take();
                System.out.printf("MessageWriter %d: Sending message\n%s\n", hubId, message);
                socket.getOutputStream().write(message.getBytes());
            } catch (InterruptedException e) {
                // Error while taking the message
                System.out.printf("MessageWriter %d: Error while taking the message\n%s\n", hubId, e.getMessage());
                break;
            } catch (Exception e) {
                // Error while writing the message
                System.out.printf("MessageWriter %d: Error while writing the message\n%s\n", hubId, e.getMessage());
                break;
            }
        }
        System.out.printf("MessageWriter %d: Stopped\n", hubId);
    }
    // Send a message to the hub through the message queue
    public boolean enqueueMessage(String message) {
        System.out.printf("MessageWriter %d: Enqueue message\n%s\n", hubId, message);
        return messageQueue.offer(message);
    }
}
