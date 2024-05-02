package com.ssafy.mimo.user.repository;

import com.ssafy.mimo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
