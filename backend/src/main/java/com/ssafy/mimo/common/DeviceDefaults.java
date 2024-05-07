package com.ssafy.mimo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceDefaults {
	LIGHT_WAKEUP_COLOR("FFFFFF"),
	LIGHT_CUR_COLOR("FFFFFF"),
	LAMP_WAKEUP_COLOR("FFFFFF"),
	LAMP_CUR_COLOR("FFFFFF");

	private final String value;
}
