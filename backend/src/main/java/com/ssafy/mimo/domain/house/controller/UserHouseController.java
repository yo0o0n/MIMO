package com.ssafy.mimo.domain.house.controller;

import com.ssafy.mimo.domain.house.dto.HouseResponseDto;
import com.ssafy.mimo.domain.house.service.UserHouseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/houses")
@RequiredArgsConstructor
public class UserHouseController {

	private final UserHouseService userHouseService;

	@GetMapping
	public ResponseEntity<?> getHouses(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		List<HouseResponseDto> houses = userHouseService.getHouses(userId);
		return ResponseEntity.ok(houses);
	}

}
