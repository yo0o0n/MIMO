package com.ssafy.mimo.domain.house.controller;

import com.ssafy.mimo.domain.house.dto.HouseResponseDto;
import com.ssafy.mimo.domain.house.dto.HouseUpdateRequestDto;
import com.ssafy.mimo.domain.house.service.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

	private final HouseService houseService;

	@GetMapping
	public ResponseEntity<?> getHouses(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		List<HouseResponseDto> houses = houseService.getHouses(userId);
		return ResponseEntity.ok(houses);
	}

	@PutMapping("/{userHouseId}")
	public ResponseEntity<?> updateInfo(HttpServletRequest request,
										@RequestBody HouseUpdateRequestDto houseUpdateRequestDto,
										@PathVariable("userHouseId") Long userHouseId) {
		Long userId = (Long) request.getAttribute("userId");
		houseService.updateInfo(userId, userHouseId, houseUpdateRequestDto);
		return ResponseEntity.ok(new HashMap<String, Object>() {{
			put("nickname", houseUpdateRequestDto.getNickname());
			put("status", 200);
			put("message", "Success");
		}});
	}

	@DeleteMapping("/{userHouseId}")
	public ResponseEntity<Void> deleteUserHouse(HttpServletRequest request,
										   @PathVariable("userHouseId") Long userHouseId) {
		Long userId = (Long) request.getAttribute("userId");

		houseService.deleteUserHouse(userId, userHouseId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{userHouseId}/setHome")
	public ResponseEntity<?> updateHouseStatus(HttpServletRequest request,
										@PathVariable("userHouseId") Long userHouseId) {
		Long userId = (Long) request.getAttribute("userId");
		boolean isHome = houseService.updateHouseStatus(userId, userHouseId);
		return isHome ?
				ResponseEntity.ok().body("{\"is_home\": \"true\"}") :
				ResponseEntity.ok().body("{\"is_home\": \"false\"}");
	}

}
