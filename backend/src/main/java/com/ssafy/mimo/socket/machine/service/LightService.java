package com.ssafy.mimo.socket.machine.service;

public interface LightService {
    void setCurrentColor(String color);

    void setWakeupColor(String wakeupColor, int time);

    void turnOff();

    boolean getState();

    String getCurrentColor();
}
