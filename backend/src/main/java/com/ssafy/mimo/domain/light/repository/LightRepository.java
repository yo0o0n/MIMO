package com.ssafy.mimo.domain.light.repository;

import com.ssafy.mimo.domain.light.entity.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightRepository extends JpaRepository<Light, Long> {
}
