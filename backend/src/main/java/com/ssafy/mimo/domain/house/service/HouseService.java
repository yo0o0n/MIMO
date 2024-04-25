package com.ssafy.mimo.domain.house.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.house.dto.HouseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseService {
	public HouseDto getHouses() {
		return HouseDto.builder()
			.id(1L)
			.nickname("집")
			.address("서울시 강남구")
			.isHome(true)
			.devices(List.of("TV", "냉장고", "세탁기"))
			.build();
	}
}
