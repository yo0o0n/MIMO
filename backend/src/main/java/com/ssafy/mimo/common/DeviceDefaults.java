package com.ssafy.mimo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceDefaults {
	LIGHT_WAKEUP_COLOR("FFFFFF"),
	LIGHT_CUR_COLOR("E0854D"),
	LAMP_WAKEUP_COLOR("FFFFFF"),
	LAMP_CUR_COLOR("E0854D"),
	CURTAIN_OPEN_DEGREE("100"),
	WINDOW_OPEN_DEGREE("100"),
	WAKEUP_TIME("07:00:00"),
	NIGHT_START_HOUR("15"),
	NIGHT_END_HOUR("11");

	private final String value;
}
