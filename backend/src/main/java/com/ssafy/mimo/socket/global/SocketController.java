package com.ssafy.mimo.socket.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mimo.socket.machine.service.LightService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Controller
public class SocketController {

    private ServerSocket serverSocket;
    private final ApplicationContext applicationContext;

    @Autowired
    public SocketController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                // 새로운 클라이언트 연결 처리
                new ClientHandler(socket, applicationContext).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 클라이언트 처리를 위한 별도의 스레드 클래스
    private static class ClientHandler extends Thread {
        private final Socket socket;
        private final ApplicationContext applicationContext;

        public ClientHandler(Socket socket, ApplicationContext applicationContext) {
            this.socket = socket;
            this.applicationContext = applicationContext;
        }

        @Override
        public void run() {
            JSONParser parser = new JSONParser();
            ObjectMapper objectMapper = new ObjectMapper();

            LightService lightService = applicationContext.getBean(LightService.class);
//            MoodLightService moodLightService = applicationContext.getBean(MoodLightService.class);
//            CurtainService curtainService = applicationContext.getBean(CurtainService.class);
//            WindowService windowService = applicationContext.getBean(WindowService.class);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(clientMessage)) {
                        break;
                    }

                    JSONObject requestDTO = (JSONObject) parser.parse(clientMessage);
                    JSONObject responseJson = new JSONObject();
                    String deviceType = (String) requestDTO.get("type");
                    Long requestNumber = (Long) requestDTO.get("requestNumber");
                    responseJson.put("requestNumber", requestNumber);

                    switch (deviceType) {
                        case "light":
                            handleLightRequest(requestDTO, lightService, responseJson);
                            break;
//                        case "moodlight":
//                            handleMoodLightRequest(requestDTO, moodLightService, responseJson);
//                            break;
//                        case "curtain":
//                            handleCurtainRequest(requestDTO, curtainService, responseJson);
//                            break;
//                        case "window":
//                            handleWindowRequest(requestDTO, windowService, responseJson);
//                            break;
                        default:
                            responseJson.put("error", "Invalid device type");
                            break;
                    }
                    writer.println(responseJson.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleLightRequest(JSONObject requestDTO, LightService lightService, JSONObject responseJson) {
            String requestName = (String) requestDTO.get("requestName");
            switch (requestName) {
                case "setCurrentColor":
                    String color = (String) requestDTO.get("color");
                    lightService.setCurrentColor(color);
                    responseJson.put("result", "Color set to " + color);
                    break;
                case "setWakeupColor":
                    String wakeupColor = (String) requestDTO.get("color");
                    int time = ((Long) requestDTO.get("time")).intValue();
                    lightService.setWakeupColor(wakeupColor, time);
                    responseJson.put("result", "Wakeup color set to " + wakeupColor + " after " + time + " minutes");
                    break;
                case "setStateOff":
                    lightService.turnOff();
                    responseJson.put("result", "Light turned off");
                    break;
                case "getState":
                    boolean isOn = lightService.getState();
                    responseJson.put("result", "Light is " + (isOn ? "on" : "off"));
                    break;
                case "getCurrentColor":
                    String currentColor = lightService.getCurrentColor();
                    responseJson.put("result", "Current color is " + currentColor);
                    break;
                // 다른 조명 관련 요청 처리 부분 추가 가능
            }
        }

        // handleMoodLightRequest, handleCurtainRequest, handleWindowRequest 추가 필요
    }
}
