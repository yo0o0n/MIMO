package com.ssafy.mimo.domain.light.entity;

import com.ssafy.mimo.common.BaseDeviceEntity;
import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Light extends BaseDeviceEntity {
    @NotNull
    private String wakeupColor;
    @NotNull
    private String curColor;
}
