package com.ssafy.mimo.domain.hub.entity;

import com.ssafy.mimo.common.BaseUnregisterableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HUB")
public class Hub extends BaseUnregisterableEntity {
//    @ManyToOne
//    private House house;
    @NotNull
    private Boolean isRegistered;
    @NotNull
    private String serialNumber;
    @NotNull
    private String nickname;
}
