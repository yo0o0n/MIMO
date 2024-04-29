package com.ssafy.mimo.socket.machine.service;

import com.ssafy.mimo.domain.light.entity.Light;
import com.ssafy.mimo.socket.machine.repository.LightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LightServiceImpl implements LightService {
    private final LightRepository lightRepository;

    @Autowired
    public LightServiceImpl(LightRepository lightRepository) {
        this.lightRepository = lightRepository;
    }

    @Override
    public void setCurrentColor(String color) {
        Light light = lightRepository.findById(1L).orElseThrow(() -> new RuntimeException("Light not found"));
        light.setColor(color);
        lightRepository.save(light);
    }

    @Override
    public void setWakeupColor(String wakeupColor, int time) {
        // This method would ideally use some sort of scheduler or asynchronous handling to set the color after the specified time
    }

    @Override
    public void turnOff() {
        Light light = lightRepository.findById(1L).orElseThrow(() -> new RuntimeException("Light not found"));
        light.setOn(false);
        lightRepository.save(light);
    }

    @Override
    public boolean getState() {
        Light light = lightRepository.findById(1L).orElseThrow(() -> new RuntimeException("Light not found"));
        return light.isOn();
    }

    @Override
    public String getCurrentColor() {
        Light light = lightRepository.findById(1L).orElseThrow(() -> new RuntimeException("Light not found"));
        return light.getColor();
    }
}
