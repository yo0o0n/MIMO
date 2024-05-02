package com.ssafy.mimo.common;

import com.ssafy.mimo.domain.hub.entity.Hub;
import com.ssafy.mimo.user.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseDeviceEntity extends BaseUnregisterableEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;
    @ManyToOne
    @JoinColumn(name = "HUB_ID")
    @NotNull
    private Hub hub;
    @NotNull
    private String nickname;
    private boolean isAccessible;
}
