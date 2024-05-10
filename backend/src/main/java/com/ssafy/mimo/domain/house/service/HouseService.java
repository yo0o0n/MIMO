package com.ssafy.mimo.domain.house.service;

import com.ssafy.mimo.domain.curtain.repository.CurtainRepository;
import com.ssafy.mimo.domain.house.dto.*;
import com.ssafy.mimo.domain.house.entity.House;
import com.ssafy.mimo.domain.house.entity.UserHouse;
import com.ssafy.mimo.domain.house.repository.HouseRepository;
import com.ssafy.mimo.domain.house.repository.UserHouseRepository;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.domain.hub.repository.HubRepository;
import com.ssafy.mimo.domain.lamp.repository.LampRepository;
import com.ssafy.mimo.domain.light.repository.LightRepository;
import com.ssafy.mimo.domain.window.repository.WindowRepository;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseService {

	private final HouseRepository houseRepository;
	private final UserHouseRepository userHouseRepository;
	private final HubRepository hubRepository;
	private final UserRepository userRepository;
	private final LampRepository lampRepository;
	private final LightRepository lightRepository;
	private final WindowRepository windowRepository;
	private final CurtainRepository curtainRepository;

	public List<HouseResponseDto> getHouses(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("사용자 ID는 null 이 될 수 없습니다.");
		}

		List<UserHouse> userHouses = userHouseRepository.findAllByUserId(userId);
		Map<String, HouseResponseDto> houseMap = new LinkedHashMap<>();

		for (UserHouse userHouse : userHouses) {
			House house = userHouse.getHouse();
			HouseResponseDto houseResponseDto = HouseResponseDto.builder()
					.id(userHouse.getId())
					.nickname(userHouse.getNickname())
					.address(house.getAddress())
					.isHome(userHouse.isHome())
					.devices(new ArrayList<>())
					.build();

			// 동일한 주소의 경우, 가장 최근 것으로 업데이트
			houseMap.put(house.getAddress(), houseResponseDto);
		}

		return new ArrayList<>(houseMap.values());
	}

	public void registerHouse(Long userId, HouseRegisterRequestDto houseRegisterRequestDto) {
		// SerialNumber를 통해 이미 생성된 허브를 조회
		Hub hub = hubRepository.findBySerialNumber(houseRegisterRequestDto.serialNumber())
				.orElseThrow(() -> new IllegalArgumentException("등록된 허브가 없습니다. 허브 등록이 필요합니다."));
		House house = new House();
		house.setAddress(houseRegisterRequestDto.address());
		houseRepository.save(house);

		// 허브와 집을 연결
		hub.setHouse(house);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		// 기존에 등록된 집 중 isHome이 true인 경우 false로 업데이트
		List<UserHouse> myHomes = userHouseRepository.findByUserAndIsHome(user, true);
		for (UserHouse myHome : myHomes) {
			myHome.setHome(false);
			userHouseRepository.save(myHome);
		}

		// 새 집 등록
		UserHouse userHouse = UserHouse.builder()
				.user(user)
				.house(house)
				.nickname(houseRegisterRequestDto.nickname())
				.isHome(true)
				.build();
		userHouseRepository.save(userHouse);
	}

	public void unregisterHouse(Long userId, Long userHouseId) {
		UserHouse userHouse = userHouseRepository.findById(userHouseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자 집을 찾을 수 없습니다: " + userHouseId));

		User user = userHouse.getUser();
		// UserHouse의 사용자 ID와 요청한 사용자 ID가 같은지 확인
		if (!user.getId().equals(userId)) {
			throw new IllegalArgumentException("집을 삭제할 권한이 없습니다.");
		}

		userHouse.setActive(false);
		userHouseRepository.save(userHouse);

		// 해당 House에 더 이상 활성화된 UserHouse가 없는지 확인
		House house = userHouse.getHouse();
		if (userHouseRepository.findByHouseAndIsActive(house, true).isEmpty()) {
			house.setActive(false);
			houseRepository.save(house);
		}
	}

	public void updateHouseNickname(Long userId, Long userHouseId, HouseNicknameRequestDto houseNicknameRequestDto) {
		UserHouse userHouse = userHouseRepository.findById(userHouseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자 집을 찾을 수 없습니다: " + userHouseId));

		if (!userHouse.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("집을 수정할 권한이 없습니다.");
		}

		userHouse.updateNickname(houseNicknameRequestDto.nickname());
	}

	public boolean updateHouseStatus(Long userId, Long userHouseId) {
		// 현재 거주지인 집을 찾아 해제
		UserHouse currentHome = userHouseRepository.findHomeByUserIdAndIsHome(userId, true);
		if (!currentHome.getId().equals(userHouseId)) {
			currentHome.deactivateHome();
			userHouseRepository.save(currentHome);
		}

		// 새로운 집을 현재 거주지로 설정
		UserHouse newHome = userHouseRepository.findByIdAndUserId(userHouseId, userId).orElse(null);
		if (newHome == null) return false; // 새 집이 없는 경우

        newHome.activateHome();
        userHouseRepository.save(newHome);

        return true;
    }

	public HouseDeviceResponseDto getDevices(Long userId, Long userHouseId) {
		// 입력된 userHouseId로 UserHouse 객체 찾기
		UserHouse userHouse = userHouseRepository.findById(userHouseId)
				.orElseThrow(() -> new RuntimeException("해당 ID를 가진 사용자 집을 찾을 수 없습니다: " + userHouseId));

		if (!userHouse.isHome()) {
			throw new RuntimeException("UserHouse (" + userHouseId + ")는 현재 거주지가 아닙니다.");
		}

		// UserHouse가 연결된 House 찾기
		House house = userHouse.getHouse();
		if (house == null) {
			throw new RuntimeException("UserHouse (" + userHouseId + ")에 연결된 House가 없습니다.");
		}

		// House에 등록된 모든 Hub 찾기
		List<Hub> hubs = hubRepository.findByHouseId(house.getId());

		// 모든 디바이스 타입 별로 조회 및 DTO 변환
		List<DeviceDetailDto> allDevices = new ArrayList<>();
		for (Hub hub : hubs) {
			Long hubId = hub.getId();
			allDevices.addAll(lampRepository.findByHubId(hubId).stream()
					.map(device -> DeviceDetailDto.builder()
							.userId(device.getUser().getId())
							.hubId(hubId)
							.deviceId(device.getId())
							.nickname(device.getNickname())
							.isAccessible(device.isAccessible())
							.type("lamp")
							.build())
					.toList());

			allDevices.addAll(lightRepository.findByHubId(hubId).stream()
					.map(device -> DeviceDetailDto.builder()
							.userId(device.getUser().getId())
							.hubId(hubId)
							.deviceId(device.getId())
							.nickname(device.getNickname())
							.isAccessible(device.isAccessible())
							.type("light")
							.build())
					.toList());

			allDevices.addAll(windowRepository.findByHubId(hubId).stream()
					.map(device -> DeviceDetailDto.builder()
							.userId(device.getUser().getId())
							.hubId(hubId)
							.deviceId(device.getId())
							.nickname(device.getNickname())
							.isAccessible(device.isAccessible())
							.type("window")
							.build())
					.toList());

			allDevices.addAll(curtainRepository.findByHubId(hubId).stream()
					.map(device -> DeviceDetailDto.builder()
							.userId(device.getUser().getId())
							.hubId(hubId)
							.deviceId(device.getId())
							.nickname(device.getNickname())
							.isAccessible(device.isAccessible())
							.type("curtain")
							.build())
					.toList());
		}

		return HouseDeviceResponseDto.builder()
				.houseId(house.getId())
				.nickname(userHouse.getNickname())
				.address(house.getAddress())
				.isHome(userHouse.isHome())
				.devices(allDevices)
				.build();
	}

	public House findHouseById(Long houseId) {
		return houseRepository.findById(houseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 집이 존재하지 않습니다."));
	}
}
