package com.ssafy.mimo.user.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.mimo.user.dto.KakaoInfoDto;
import com.ssafy.mimo.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoOauthService {
	private final UserService userService;

	// 카카오Api 호출해서 AccessToken으로 유저정보 가져오기
	public Map<String, Object> getUserAttributesByToken(String accessToken) {
		return WebClient.create()
			.get()
			.uri("https://kapi.kakao.com/v2/user/me")
			.headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	// 카카오API에서 가져온 유저정보를 DB에 저장
	public User getUserProfileByToken(String accessToken) {
		Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
		KakaoInfoDto kakaoInfoDto = new KakaoInfoDto(userAttributesByToken);
		User user = userService.findByProviderId(kakaoInfoDto.getId());
		if (user == null) {
			user = User.builder()
				.providerId(kakaoInfoDto.getId())
				.email(kakaoInfoDto.getEmail())
				.build();
		} else {
			user.setEmail(kakaoInfoDto.getEmail());
		}
		userService.save(user);
		return user;
	}
}