package com.ssafy.mimo.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoResponseDto {

	private Long id;
	private String connected_at;
	private KakaoAccount kakao_account;

	@Getter
	@Setter
	public static class KakaoAccount {
		private Boolean has_email;
		private Boolean emails_needs_agreement;
		private Boolean is_email_valid;
		private Boolean is_email_verified;
		private String email;
	}
}
