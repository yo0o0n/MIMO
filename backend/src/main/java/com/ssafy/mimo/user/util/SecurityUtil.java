package com.ssafy.mimo.user.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssafy.mimo.exception.CustomException;
import com.ssafy.mimo.exception.ErrorCode;
import com.ssafy.mimo.user.entity.User;

public class SecurityUtil {
	private SecurityUtil() {}

	public static long getCurrentUserId() {

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		long userId;
		if (authentication.getPrincipal() instanceof User userPrincipal) {
			userId = userPrincipal.getId();
		} else {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}

		return userId;
	}
}