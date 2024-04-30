package com.ssafy.mimo.domain.house.entity;

import com.ssafy.mimo.common.BaseDeletableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HOUSE")
public class House extends BaseDeletableEntity {

    @NotNull
    private Boolean isHome;

    @NotNull
    private String nickname;

}
