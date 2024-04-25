package com.ssafy.mimo.domain.house.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mimo.domain.house.dto.HouseDto;
import com.ssafy.mimo.domain.house.service.HouseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/houses")
@RequiredArgsConstructor
public class HouseController {
	private final HouseService houseService;

	@GetMapping
	public ResponseEntity<HouseDto> getHouses() {
		return ResponseEntity.ok(houseService.getHouses());
	}
}
