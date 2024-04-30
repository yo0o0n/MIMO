package com.ssafy.mimo.domain.house.repository;

import com.ssafy.mimo.domain.house.entity.UserHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHouseRepository extends JpaRepository<UserHouse, Long> {

    List<UserHouse> findAllByUser_Id(Long userId);

}
