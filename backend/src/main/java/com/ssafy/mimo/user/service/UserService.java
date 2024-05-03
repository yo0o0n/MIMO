package com.ssafy.mimo.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
	}
}