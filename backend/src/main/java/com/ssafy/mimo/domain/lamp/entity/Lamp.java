package com.ssafy.mimo.domain.lamp.entity;

import org.jetbrains.annotations.NotNull;

import com.ssafy.mimo.common.BaseDeviceEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Lamp extends BaseDeviceEntity {
	@NotNull
	private String wakeupColor;
	@NotNull
	private String curColor;
}
