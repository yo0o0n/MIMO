package com.ssafy.mimo.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.mimo.user.dto.UserDto;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private  final UserRepository userRepository;

	// 유저 id로 검색하기
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
	}

	public User findByProviderId(Long id) {
		return userRepository.findByProviderId(id).orElse(null);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public User findByRefreshToken(String refreshToken) {
		return userRepository.findByRefreshToken(refreshToken).orElse(null);
	}


	public void updateRefreshToken(User user) {
		userRepository.save(user);
	}
}