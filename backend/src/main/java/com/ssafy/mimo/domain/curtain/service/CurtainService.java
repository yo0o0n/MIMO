package com.ssafy.mimo.domain.curtain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.curtain.dto.CurtainRegisterRequestDto;
import com.ssafy.mimo.domain.curtain.dto.CurtainRegisterResponseDto;
import com.ssafy.mimo.domain.curtain.entity.Curtain;
import com.ssafy.mimo.domain.curtain.repository.CurtainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CurtainService {
	private final CurtainRepository curtainRepository;
	private final CurtainRegisterService curtainRegisterService;

	// 커튼 등록하는 메서드
	public CurtainRegisterResponseDto registerCurtain(Long userId, CurtainRegisterRequestDto curtainRegisterRequestDto) {
		String macAddress = curtainRegisterRequestDto.macAddress();

		// db 에 없는 신규 기기인 경우
		if (curtainRepository.findByMacAddress(macAddress).isEmpty()) {
			return curtainRegisterService.registerNewCurtain(userId, curtainRegisterRequestDto);
		}

		// 기존에 db에 있는 경우 해당 커튼 불러오기
		Curtain curtain = findCurtainByMacAddress(macAddress);

		// 등록해제 되어 있는 기기인 경우 다시 등록 절차
		if (!curtain.isRegistered()) {
			return curtainRegisterService.registerOldCurtain(userId, curtain, curtainRegisterRequestDto);
		}

		// 이미 등록된 기기인 경우 오류 안내
		throw new IllegalArgumentException("이미 등록된 커튼입니다.");
	}


	// 커튼 mac 주소로 커튼 찾기
	private Curtain findCurtainByMacAddress(String macAddress) {
		return curtainRepository.findByMacAddress(macAddress)
				.orElseThrow(() -> new IllegalArgumentException("해당 MAC 주소를 가진 커튼이 존재하지 않습니다."));
	}

	// 커튼 id로 커튼 찾기
	private Curtain findCurtainById(Long curtainId) {
		return curtainRepository.findById(curtainId)
				.orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 커튼이 존재하지 않습니다."));
	}

	// 기기 주인인지 확인
}
