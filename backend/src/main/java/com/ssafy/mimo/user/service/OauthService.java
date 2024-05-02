package com.ssafy.mimo.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.mimo.exception.CustomException;
import com.ssafy.mimo.exception.ErrorCode;
import com.ssafy.mimo.user.dto.UserDto;
import com.ssafy.mimo.user.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OauthService {
	private final UserService userService;
	private final JwtTokenService jwtTokenService;
	private final KakaoOauthService kakaoOauthService;

	//카카오 로그인
	public String loginWithKakao(String accessToken, HttpServletResponse response) {
		User user = kakaoOauthService.getUserProfileByToken(accessToken);
		return getTokens(user.getId(), response);
	}

	//액세스토큰, 리프레시토큰 생성
	public String getTokens(Long id, HttpServletResponse response) {
		final String accessToken = jwtTokenService.createAccessToken(id.toString());
		final String refreshToken = jwtTokenService.createRefreshToken();

		User user = userService.findUserById(id);
		user.setRefreshToken(refreshToken);
		userService.save(user);

		jwtTokenService.addRefreshTokenToCookie(refreshToken, response);
		return accessToken;
	}

	// 리프레시 토큰으로 액세스토큰 새로 갱신
	public String refreshAccessToken(String refreshToken) {
		User user = userService.findByRefreshToken(refreshToken);
		if(user == null) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		if(!jwtTokenService.validateToken(refreshToken)) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		return jwtTokenService.createAccessToken(user.getId().toString());
	}
}