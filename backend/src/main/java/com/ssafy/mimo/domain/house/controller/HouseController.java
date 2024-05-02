package com.ssafy.mimo.domain.house.controller;

import com.ssafy.mimo.domain.house.dto.HouseResponseDto;
import com.ssafy.mimo.domain.house.service.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

	private final HouseService houseService;

	@GetMapping
	public ResponseEntity<?> getHouses(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		List<HouseResponseDto> houses = houseService.getHouses(userId);
		return ResponseEntity.ok(houses);
	}


}
