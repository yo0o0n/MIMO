package com.ssafy.mimo.domain.window.entity;

import com.ssafy.mimo.common.BaseDeviceEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
public class SlidingWindow extends BaseDeviceEntity {
}
