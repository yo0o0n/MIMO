package com.ssafy.mimo.domain.lamp.entity;

import jakarta.persistence.FetchType;
import org.jetbrains.annotations.NotNull;

import com.ssafy.mimo.common.BaseDeviceEntity;
import com.ssafy.mimo.domain.hub.entity.Hub;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Lamp extends BaseDeviceEntity {

	@NotNull
	private String wakeupColor;

	@NotNull
	private String curColor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HUB_ID")
	@Nullable
	private Hub hub;
}
