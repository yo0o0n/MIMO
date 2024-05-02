package com.ssafy.mimo.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.mimo.user.dto.UserDto;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.mapper.UserMapper;
import com.ssafy.mimo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private  final UserRepository userRepository;
	// private final UserMapper userMapper;

	// 유저 id로 검색하기
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	// public void save(UserDto userDto) {
	// 	userMapper.save(userDto);
	// }
	//
	// public UserDto findByProviderId(Long id) {
	// 	return userMapper.findByProviderId(id);
	// }
	//
	// public UserDto findByRefreshToken(String refreshToken) {
	// 	return userMapper.findByRefreshToken(refreshToken);
	// }
	//
	// public void update(UserDto userDto) {
	// 	userMapper.update(userDto);
	// }
	//
	// public void updateRefreshToken(UserDto userDto) {
	// 	userMapper.updateRefreshToken(userDto);
	// }
}