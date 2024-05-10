package com.ssafy.mimo.domain.curtain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.mimo.domain.curtain.entity.Curtain;

public interface CurtainRepository extends JpaRepository<Curtain, Long>{
	Optional<Curtain> findByMacAddress(String macAddress);
}
