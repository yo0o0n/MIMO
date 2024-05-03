package com.ssafy.mimo.domain.house.service;

import com.ssafy.mimo.domain.house.dto.HouseResponseDto;
import com.ssafy.mimo.domain.house.dto.HouseUpdateRequestDto;
import com.ssafy.mimo.domain.house.entity.House;
import com.ssafy.mimo.domain.house.entity.UserHouse;
import com.ssafy.mimo.domain.house.repository.HouseRepository;
import com.ssafy.mimo.domain.house.repository.UserHouseRepository;
import com.ssafy.mimo.user.entity.User;
import com.ssafy.mimo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseService {

	private final HouseRepository houseRepository;
	private final UserHouseRepository userHouseRepository;
	private final UserRepository userRepository;

	public List<HouseResponseDto> getHouses(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));

		List<UserHouse> userHouses = userHouseRepository.findAllByUser_Id(userId);
		List<HouseResponseDto> houseList = new ArrayList<>();

		// UserHouse 목록을 순회하며 HouseResponseDto 생성
		for (UserHouse userHouse : userHouses) {
			House house = userHouse.getHouse();
			HouseResponseDto houseResponseDto = HouseResponseDto.builder()
					.id(userHouse.getId())
					.nickname(userHouse.getNickname())
					.address(house.getAddress())
					.isHome(true)
					.devices(new ArrayList<>())
					.build();
			houseList.add(houseResponseDto);
		}
		return houseList;
	}

	public void updateInfo(Long userId, Long userHouseId, HouseUpdateRequestDto houseUpdateRequestDto) {
		UserHouse userHouse = userHouseRepository.findById(userHouseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 UserHouse를 찾을 수 없습니다: " + userHouseId));

		if (!userHouse.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("집을 수정할 권한이 없습니다.");
		}

		userHouse.updateNickname(houseUpdateRequestDto.getNickname());
	}

	public void deleteUserHouse(Long userId, Long userHouseId) {
		UserHouse userHouse = userHouseRepository.findById(userHouseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 UserHouse를 찾을 수 없습니다: " + userHouseId));

		User user = userHouse.getUser();
		// 요청한 사용자 ID와 UserHouse의 사용자 ID가 같은지 확인
		if (!user.getId().equals(userId)) {
			throw new IllegalArgumentException("집을 삭제할 권한이 없습니다.");
		}

		userHouseRepository.delete(userHouse);
		userHouseRepository.flush();  // 즉시 데이터베이스와 동기화

		// 해당 House에 더 이상 연결된 UserHouse가 없는지 확인
		House house = userHouse.getHouse();
		if (userHouseRepository.findByHouse(house).isEmpty()) {
			houseRepository.delete(house);
		}
	}

	public boolean updateHouseStatus(Long userId, Long userHouseId) {
		// 현재 거주지인 집을 찾아 해제
		UserHouse currentHome = userHouseRepository.findCurrentHomeByUserId(userId);
		if (currentHome != null) {
			currentHome.deactivateHome();  // 직접적인 메소드 호출로 상태 변경
			userHouseRepository.save(currentHome);
		}

		// 새로운 집을 현재 거주지로 설정
		UserHouse newHome = userHouseRepository.findByIdAndUserId(userHouseId, userId);
		if (newHome != null) {
			newHome.activateHome();  // 직접적인 메소드 호출로 상태 변경
			userHouseRepository.save(newHome);
			return true;  // 성공적으로 새로운 집을 현재 거주지로 설정
		}
		return false;  // 실패 (주어진 ID의 집을 찾지 못함)
	}

	public House findHouseById(Long houseId) {
		return houseRepository.findById(houseId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 집이 존재하지 않습니다."));
	}
}
