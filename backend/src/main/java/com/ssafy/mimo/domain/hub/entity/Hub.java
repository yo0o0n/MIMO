package com.ssafy.mimo.domain.hub.entity;

import com.ssafy.mimo.common.BaseUnregisterableEntity;
import com.ssafy.mimo.domain.house.entity.House;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "HUB")
public class Hub extends BaseUnregisterableEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID")
    @Nullable
    private House house;
    @NotNull
    private String serialNumber;
}
