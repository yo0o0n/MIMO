package com.ssafy.mimo.common;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseUnregisterableEntity extends BaseEntity {
    @Nullable
    private LocalDateTime registeredDttm;
    @Nullable
    private LocalDateTime unregisteredDttm;
    @NotNull
    private Boolean isRegistered = false;
    @NotNull
    private String nickname;
}
