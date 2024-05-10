package com.ssafy.mimo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceDefaults {
	LIGHT_WAKEUP_COLOR("FFFFFF"),
	LIGHT_CUR_COLOR("FFFFFF"),
	LAMP_WAKEUP_COLOR("FFFFFF"),
	LAMP_CUR_COLOR("FFFFFF"),
	CURTAIN_OPEN_DEGREE("100"),
	WINDOW_OPEN_DEGREE("100");

	private final String value;
}
