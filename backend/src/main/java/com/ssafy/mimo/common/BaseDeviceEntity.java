package com.ssafy.mimo.common;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.user.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseDeviceEntity extends BaseUnregisterableEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Nullable
    private User user;
    @ManyToOne
    @JoinColumn(name = "HUB_ID")
    @Nullable
    private Hub hub;
    @Nullable
    private String nickname;
    private boolean isAccessible;
}
