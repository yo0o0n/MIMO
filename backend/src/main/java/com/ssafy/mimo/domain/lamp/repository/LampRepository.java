package com.ssafy.mimo.domain.lamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.mimo.domain.lamp.entity.Lamp;

@Repository
public interface LampRepository extends JpaRepository<Lamp, Long> {
}
