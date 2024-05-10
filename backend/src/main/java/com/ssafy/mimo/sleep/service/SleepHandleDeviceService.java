package com.ssafy.mimo.sleep.service;

import static com.ssafy.mimo.common.SleepLevel.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.house.dto.DeviceDetailDto;
import com.ssafy.mimo.domain.house.dto.HouseDeviceResponseDto;
import com.ssafy.mimo.domain.house.entity.UserHouse;
import com.ssafy.mimo.domain.house.service.HouseService;
import com.ssafy.mimo.sleep.dto.SleepDataDto;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SleepHandleDeviceService {
	private final UserService userService;
	private final HouseService houseService;
	private final DeviceHandlerService deviceHandlerService;

	public void handleDeviceBySleepLevel(Long userId, SleepDataDto sleepDataDto) {
		Integer sleepLevel = sleepDataDto.sleepLevel();
		User user = userService.findUserById(userId);

		// 유저의 현재 집에 연결된 모든 기기 불러오기
		List<DeviceDetailDto> devices = findDevicesAtHome(userId, user);

		// 잠에 들면 동작하는 기기 제어
		if (sleepLevel == LIGHT_SLEEP.getValue()) {
			// devices.forEach();
			return;
		}

		// 완전히 깨어나면 동작하는 기기 제어
		if (sleepLevel == AWAKE.getValue()) {
			// devices.forEach();
			return;
		}

		// 아침 렘 수면 동작 들어서면 동작하는 기기 제어
		if (sleepLevel == REM.getValue()) {
			// devices.forEach();
		}
		return;
	}



	// 현재 집에 연결된 모든 기기 불러오는 메서드
	private List<DeviceDetailDto> findDevicesAtHome(Long userId, User user) {
		// 유저에 연결된 house 중 현재 집 id 찾기
		Long userHouseId = user.getUserHouse().stream()
			.filter(UserHouse::isHome)
			.findFirst()
			.map(UserHouse::getId)
			.orElseThrow(() -> new IllegalArgumentException("현재 집으로 설정된 집이 없습니다."));

		// 유저의 현재 집에 연결된 모든 기기 불러오기
		List<DeviceDetailDto> devices = houseService.getDevices(userId, userHouseId).devices();

		return devices;
	}
}
