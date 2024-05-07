package com.ssafy.mimo.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
	}

	// 토큰을 통해 유저 아이디를 찾는 메소드
	public Long getUserId(String token) {
		return Long.parseLong(jwtTokenProvider.getUserPk(token));
	}
}
