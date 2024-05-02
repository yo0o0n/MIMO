package com.ssafy.mimo.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OauthRequestDto {
	private String accessToken;
}