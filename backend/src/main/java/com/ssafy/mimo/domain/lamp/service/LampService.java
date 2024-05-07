package com.ssafy.mimo.domain.lamp.service;

import static com.ssafy.mimo.common.DeviceDefaults.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.service.HubService;
import com.ssafy.mimo.domain.lamp.dto.LampRegisterRequestDto;
import com.ssafy.mimo.domain.lamp.dto.LampRegisterResponseDto;
import com.ssafy.mimo.domain.lamp.entity.Lamp;
import com.ssafy.mimo.domain.lamp.repository.LampRepository;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LampService {
	private final LampRepository lampRepository;
	private final UserService userService;
	private final HubService hubService;

	// 무드등 등록하는 메서드
	public LampRegisterResponseDto registerLamp(Long userId, LampRegisterRequestDto lampRegisterRequestDto) {
		Long hubId = lampRegisterRequestDto.hubId();
		String nickname = lampRegisterRequestDto.nickname();
		String macAddress = lampRegisterRequestDto.macAddress();

		// 유저와 허브 객체 찾기
		User user = userService.findUserById(userId);
		Hub hub = hubService.findHubById(hubId);

		// 램프 객체 생성 및 저장
		Lamp lamp = Lamp.builder()
			.user(user)
			.hub(hub)
			.registeredDttm(LocalDateTime.now())
			.isRegistered(true)
			.isAccessible(true)
			.nickname(nickname)
			.macAddress(macAddress)
			.wakeupColor(LAMP_WAKEUP_COLOR.getValue())
			.curColor(LAMP_CUR_COLOR.getValue())
			.build();
		lampRepository.save(lamp);

		return LampRegisterResponseDto.builder()
			.lampId(lamp.getId())
			.nickname(lamp.getNickname())
			.macAddress(lamp.getMacAddress())
			.build();
	}

	// 무드등 등록해제하는 메서드
	public String unregisterLamp(Long userId, Long lampId) {
		Lamp lamp = lampRepository.findById(lampId)
			.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 램프가 존재하지 않습니다."));

		if (!lamp.isRegistered()) {
			return "이미 등록 해제된 램프입니다.";
		}

		if (!lamp.getUser().getId().equals(userId)) {
			return "권한이 없는 사용자입니다.";
		}

		// 무드등 객체 수정 및 저장
		lamp.setUser(null);
		lamp.setHub(null);
		lamp.setUnregisteredDttm(LocalDateTime.now());
		lamp.setRegistered(false);
		lampRepository.save(lamp);

		return "무드등이 등록 해제되었습니다.";
	}
}
