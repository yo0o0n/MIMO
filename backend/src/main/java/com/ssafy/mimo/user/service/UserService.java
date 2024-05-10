package com.ssafy.mimo.user.service;

import com.ssafy.mimo.user.dto.MyInfoResponseDto;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

	public MyInfoResponseDto getHomeAndHubInfo(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		boolean hasHome = false;
		boolean hasHub = false;

		if (user != null && !user.getUserHouse().isEmpty()) {
			hasHome = true;
			hasHub = user.getUserHouse().stream()
					.anyMatch(userHouse -> !userHouse.getHouse().getHub().isEmpty());
		}

		return MyInfoResponseDto.builder()
				.userId(userId)
				.hasHome(hasHome)
				.hasHub(hasHub)
				.build();
	}
}
