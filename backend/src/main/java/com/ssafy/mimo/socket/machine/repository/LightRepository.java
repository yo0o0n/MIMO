package com.ssafy.mimo.socket.machine.repository;

import com.ssafy.mimo.socket.machine.entity.Light;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightRepository extends JpaRepository<Light, Long> {
    void updateColor(String color);

    void updateStatus(boolean isOn);
}
