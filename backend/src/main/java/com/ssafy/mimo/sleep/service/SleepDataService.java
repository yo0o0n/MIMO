package com.ssafy.mimo.sleep.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.sleep.dto.SleepDataDto;
import com.ssafy.mimo.sleep.entity.SleepData;
import com.ssafy.mimo.sleep.repository.SleepDataRepository;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SleepDataService {
	private final SleepDataRepository sleepDataRepository;
	private final UserService userService;
	private final SleepHandleDeviceService sleepHandleDeviceService;

	public String handleSleepData(Long userId, SleepDataDto sleepDataDto) {
		// 들어온 수면 데이터를 먼저 저장
		saveSleepData(userId, sleepDataDto);

		// 수면 데이터에 따라 IoT 기기를 제어
		sleepHandleDeviceService.handleDeviceBySleepLevel(userId, sleepDataDto);

		return "수면 데이터가 성공적으로 전송되었습니다.";
	}













	// 수면 데이터를 저장하는 메서드
	private void saveSleepData(Long userId, SleepDataDto sleepDataDto) {
		User user = userService.findUserById(userId);

		SleepData sleepData = SleepData.builder()
			.user(user)
			.sleepLevel(sleepDataDto.sleepLevel())
			.build();
		sleepDataRepository.save(sleepData);
	}
}
