package com.ssafy.mimo.user.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.mimo.user.dto.UserDto;

@Mapper
public interface UserMapper {
	void save(UserDto userDto);
	UserDto findByProviderId(Long id);
	UserDto findByRefreshToken(String refreshToken);
	void update(UserDto userDto);
	void updateRefreshToken(UserDto userDto);
}