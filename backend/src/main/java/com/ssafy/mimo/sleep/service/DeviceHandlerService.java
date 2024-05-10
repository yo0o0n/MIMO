package com.ssafy.mimo.sleep.service;

import static com.ssafy.mimo.common.DeviceDefaults.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mimo.domain.common.dto.ManualControlRequestDataDto;
import com.ssafy.mimo.domain.common.dto.ManualControlRequestDto;
import com.ssafy.mimo.domain.common.service.CommonService;
import com.ssafy.mimo.domain.house.dto.DeviceDetailDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceHandlerService {
	private final CommonService commonService;

	public void handleOnSleep(DeviceDetailDto device) {
		String type = device.getType();
		switch (type) {
			case "light":
				// 조명 끄기
				ManualControlRequestDto lightManualControlRequestDto = ManualControlRequestDto.builder()
					.type("light")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setStateOff")
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(lightManualControlRequestDto);
				break;
			case "lamp":
				// 무드등 끄기
				ManualControlRequestDto lampManualControlRequestDto = ManualControlRequestDto.builder()
					.type("lamp")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setStateOff")
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(lampManualControlRequestDto);
				break;
			case "window":
				// 창문 닫기
				ManualControlRequestDto windowManualControlRequestDto = ManualControlRequestDto.builder()
					.type("window")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setState")
						.state(0)
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(windowManualControlRequestDto);
				break;
			case "curtain":
				// 커튼 닫기
				ManualControlRequestDto curtainManualControlRequestDto = ManualControlRequestDto.builder()
					.type("curtain")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setState")
						.state(0)
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(curtainManualControlRequestDto);
				break;
			default:
				throw new IllegalArgumentException("지원하지 않는 기기 타입입니다: " + type);
		}
	}

	public void handleOnWakeUp(DeviceDetailDto device) {
		// IoT 기기 제어 로직
		String type = device.getType();
		switch (type) {
			case "light":
				// 조명 켜기
				ManualControlRequestDto lightManualControlRequestDto = ManualControlRequestDto.builder()
					.type("light")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setCurrentColor")
						.color(LIGHT_WAKEUP_COLOR.getValue())
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(lightManualControlRequestDto);
				break;
			case "lamp":
				// 무드등 켜기
				ManualControlRequestDto lampManualControlRequestDto = ManualControlRequestDto.builder()
					.type("lamp")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setCurrentColor")
						.color(LAMP_WAKEUP_COLOR.getValue())
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(lampManualControlRequestDto);
				break;
			case "window":
				// 창문 열기
				ManualControlRequestDto windowManualControlRequestDto = ManualControlRequestDto.builder()
					.type("window")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setState")
						.state(100)
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(windowManualControlRequestDto);
				break;
			case "curtain":
				// 커튼 닫기
				ManualControlRequestDto curtainManualControlRequestDto = ManualControlRequestDto.builder()
					.type("curtain")
					.deviceId(device.getDeviceId())
					.data(ManualControlRequestDataDto.builder()
						.requestName("setState")
						.state(100)
						.build())
					.build();
				// IoT 기기 제어요청 보내기
				commonService.controlDevice(curtainManualControlRequestDto);
				break;
			default:
				throw new IllegalArgumentException("지원하지 않는 기기 타입입니다: " + type);
		}
	}

	public void handleOnRem(DeviceDetailDto device) {
		// IoT 기기 제어 로직
	}
}
