package com.ssafy.mimo.domain.house.controller;

import com.ssafy.mimo.domain.house.dto.*;
import com.ssafy.mimo.domain.house.service.HouseService;
import com.ssafy.mimo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "집 컨트롤러", description = "집 CRUD 관련 기능이 포함되어 있음")
@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

	private final HouseService houseService;
	private final UserService userService;

	@Operation(summary = "해당 사용자에 등록되어 있는 집 리스트 조회")
	@GetMapping
	public ResponseEntity<?> getHouses(@RequestHeader("X-AUTH-TOKEN") String token) {
		Long userId = userService.getUserId(token);
		List<HouseResponseDto> houses = houseService.getHouses(userId);
		return ResponseEntity.ok(houses);
	}

	@Operation(summary = "집 등록")
	@PostMapping
	public ResponseEntity<?> registerHouse(@RequestHeader("X-AUTH-TOKEN") String token,
											  @RequestBody HouseRegisterRequestDto houseRegisterRequestDto) {
		Long userId = userService.getUserId(token);
		HouseRegisterResponseDto registration = houseService.registerHouse(userId, houseRegisterRequestDto);
		return ResponseEntity.ok(registration);
	}

	@Operation(summary = "집 등록 해제")
	@DeleteMapping("/{userHouseId}")
	public ResponseEntity<Void> unregisterHouse(@RequestHeader("X-AUTH-TOKEN") String token,
												@PathVariable("userHouseId") Long userHouseId) {
		Long userId = userService.getUserId(token);
		houseService.unregisterHouse(userId, userHouseId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "집 별칭 변경")
	@PutMapping("/{userHouseId}")
	public ResponseEntity<String> updateHouseNickname(@RequestHeader("X-AUTH-TOKEN") String token,
													  @PathVariable("userHouseId") Long userHouseId,
													  @RequestBody HouseNicknameRequestDto houseNicknameRequestDto) {
		Long userId = userService.getUserId(token);
		houseService.updateHouseNickname(userId, userHouseId, houseNicknameRequestDto);
		return ResponseEntity.ok(houseNicknameRequestDto.nickname());
	}

	@Operation(summary = "현재 거주지 변경")
	@PutMapping("/{userHouseId}/home")
	public ResponseEntity<?> updateHouseStatus(@RequestHeader("X-AUTH-TOKEN") String token,
											   @PathVariable("userHouseId") Long userHouseId) {
		Long userId = userService.getUserId(token);
		boolean isHome = houseService.updateHouseStatus(userId, userHouseId);
		return isHome ?
				ResponseEntity.ok().body("{\"is_home\": \"true\"}") :
				ResponseEntity.ok().body("{\"is_home\": \"false\"}");
	}

	@Operation(summary = "해당 집에 등록되어 있는 기기 리스트 조회")
	@GetMapping("/{userHouseId}/devices")
	public ResponseEntity<HouseDeviceResponseDto> getDevices(@RequestHeader("X-AUTH-TOKEN") String token,
															 @PathVariable("userHouseId") Long userHouseId) {
		Long userId = userService.getUserId(token);
		HouseDeviceResponseDto devices = houseService.getDevices(userId, userHouseId);
		return ResponseEntity.ok(devices);
	}
}
