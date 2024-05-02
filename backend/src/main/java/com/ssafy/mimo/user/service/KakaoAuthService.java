package com.ssafy.mimo.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.user.dto.KakaoUserInfoResponseDto;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {

	private final KakaoUserInfo kakaoUserInfo;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User isSignedUp(String token) {
		KakaoUserInfoResponseDto userInfo = kakaoUserInfo.getUserInfo(token);

		return userRepository.findByKeyCode(userInfo.getId().toString())
			.orElseGet(() -> signup(userInfo));
	}

	@Transactional
	public User signup(KakaoUserInfoResponseDto userInfo) {
		User user = new User();
		// user 정보 설정
		user.setKeyCode(userInfo.getId().toString());
		// 필요한 경우 다른 필드도 설정
		return userRepository.save(user);
	}
}