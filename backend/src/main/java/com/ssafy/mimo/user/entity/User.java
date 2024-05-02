package com.ssafy.mimo.user.entity;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.mimo.common.BaseDeletableEntity;
import com.ssafy.mimo.domain.house.entity.UserHouse;
import com.ssafy.mimo.user.enums.UserRole;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
	private Long providerId;

	@Builder.Default
	@NotNull
	private Boolean isSuperUser = false;

	@Nullable
	private LocalTime wakeupTime;


	private long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	@Setter
	private Map<String, Object> attributes;

	public static UserPrincipal create(UserDto user) {
		List<GrantedAuthority> authorities =
			Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.getRole()));
		return new UserPrincipal(
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

	@OneToMany(mappedBy = "user")
	private List<UserHouse> userHouse;

}
