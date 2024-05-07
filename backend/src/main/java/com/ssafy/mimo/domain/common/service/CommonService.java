package com.ssafy.mimo.domain.common.service;

import com.ssafy.mimo.domain.common.dto.SocketRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {
    public String controlDevice(SocketRequestDto socketRequestDto) {
        return socketRequestDto.toString();
    }
}
