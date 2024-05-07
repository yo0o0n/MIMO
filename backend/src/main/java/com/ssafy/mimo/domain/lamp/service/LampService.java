package com.ssafy.mimo.domain.lamp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.lamp.repository.LampRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LampService {
	private final LampRepository lampRepository;
}
