package com.ssafy.mimo.domain.hub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HUB")
public class Hub extends BaseDeletableEntity {
//    @ManyToOne
//    private House house;
    @NotNull
    private Boolean isRegistered;
    @NotNull
    private String serialNumber;
    @NotNull
    private String nickname;
}
