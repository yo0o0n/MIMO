package com.ssafy.mimo.domain.window.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.mimo.domain.window.entity.SlidingWindow;

public interface WindowRepository extends JpaRepository<SlidingWindow, Long> {
	Optional<SlidingWindow> findByMacAddress(String macAddress);
}
