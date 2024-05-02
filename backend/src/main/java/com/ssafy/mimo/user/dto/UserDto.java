package com.ssafy.mimo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
	private Long id;
	private Long providerId;
	private String email;
	private String platform;
	private String refreshToken;
}