package com.ssafy.mimo.domain.house.dto;

import java.util.List;

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
public class HouseDto {
	private Long id;
	private String nickname;
	private String address;
	private Boolean isHome;
	private List<String> devices;
}
