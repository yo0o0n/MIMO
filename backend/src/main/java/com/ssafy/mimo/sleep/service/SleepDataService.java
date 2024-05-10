package com.ssafy.mimo.sleep.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.sleep.dto.SleepDataDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SleepDataService {
	public String handleSleepData(Long userId, SleepDataDto sleepDataDto) {
		// 수면 데터를 처리하는 비즈니스 로직

		return "수면 데이터가 성공적으로 처리되었습니다.";
	}
}
