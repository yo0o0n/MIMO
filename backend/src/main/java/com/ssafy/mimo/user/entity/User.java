package com.ssafy.mimo.user.entity;

import java.time.LocalTime;
import java.util.List;

import com.ssafy.mimo.domain.house.entity.UserHouse;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssafy.mimo.common.BaseDeletableEntity;

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
public class User extends BaseDeletableEntity {
	@NotNull
	private Integer providerId;

	@Builder.Default
	@NotNull
	private Boolean isSuperUser = false;

	@Nullable
	private LocalTime wakeupTime;

	@OneToMany(mappedBy = "user")
	private List<UserHouse> userHouse;
}
