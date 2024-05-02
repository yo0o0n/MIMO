package com.ssafy.mimo.user.entity;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ssafy.mimo.domain.house.entity.UserHouse;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.mimo.common.BaseDeletableEntity;
import com.ssafy.mimo.user.dto.UserDto;
import com.ssafy.mimo.user.enums.UserRole;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User extends BaseDeletableEntity implements UserDetails {
	@NotNull
	private Integer providerId;

	@Builder.Default
	@NotNull
	private Boolean isSuperUser = false;

	@Nullable
	private LocalTime wakeupTime;

	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public static User create(UserDto user) {
		List<GrantedAuthority> authorities =
			Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.getRole()));
		return new User(
			user.getId(),
			user.getEmail(),
			"",
			authorities,
			null
		);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@OneToMany(mappedBy = "user")
	private List<UserHouse> userHouse;

}
