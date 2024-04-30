package com.ssafy.mimo.domain.hub.dto;

import lombok.Builder;

@Builder
public record HubNicknameDto(
       Long hubId,
       String nickname
) {}
