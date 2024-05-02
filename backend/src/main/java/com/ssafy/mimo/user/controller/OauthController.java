package com.ssafy.mimo.user.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mimo.exception.CustomException;
import com.ssafy.mimo.exception.ErrorCode;
import com.ssafy.mimo.user.dto.OauthRequestDto;
import com.ssafy.mimo.user.dto.OauthResponseDto;
import com.ssafy.mimo.user.dto.RefreshTokenResponseDto;
import com.ssafy.mimo.user.service.OauthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OauthController {
	private final OauthService oauthService;

	@RequestMapping("/api/v1/auth")
	@PostMapping("/login/{provider}")
	public OauthResponseDto login(@PathVariable String provider, @RequestBody OauthRequestDto oauthRequestDto,
		HttpServletResponse response) {
		OauthResponseDto oauthResponseDto = new OauthResponseDto();
		switch (provider) {
			case "kakao":
				String accessToken = oauthService.loginWithKakao(oauthRequestDto.getAccessToken(), response);
				oauthResponseDto.setAccessToken(accessToken);
		}
		return oauthResponseDto;
	}

	// 리프레시 토큰으로 액세스토큰 재발급 받는 로직
	@PostMapping("/token/refresh")
	public RefreshTokenResponseDto tokenRefresh(HttpServletRequest request) {
		RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();
		Cookie[] list = request.getCookies();
		if (list == null) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		Cookie refreshTokenCookie = Arrays.stream(list)
			.filter(cookie -> cookie.getName().equals("refresh_token"))
			.collect(
				Collectors.toList())
			.get(0);

		if (refreshTokenCookie == null) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}
		String accessToken = oauthService.refreshAccessToken(refreshTokenCookie.getValue());
		refreshTokenResponseDto.setAccessToken(accessToken);
		return refreshTokenResponseDto;
	}
}