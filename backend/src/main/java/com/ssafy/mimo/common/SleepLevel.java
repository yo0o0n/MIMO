package com.ssafy.mimo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SleepLevel {
	AWAKE,
	AWAKENED,
	REM,
	LIGHT_SLEEP,
	DEEP_SLEEP
}
