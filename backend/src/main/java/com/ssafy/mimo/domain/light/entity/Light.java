package com.ssafy.mimo.domain.light.entity;

import com.ssafy.mimo.domain.hub.entity.Hub;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.jetbrains.annotations.NotNull;

import com.ssafy.mimo.common.BaseDeviceEntity;

import jakarta.persistence.Entity;
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
public class Light extends BaseDeviceEntity {

	@NotNull
	private String wakeupColor;

	@NotNull
	private String curColor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HUB_ID")
	@Nullable
	private Hub hub;
}
