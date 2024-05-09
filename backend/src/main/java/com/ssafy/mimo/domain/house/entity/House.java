package com.ssafy.mimo.domain.house.entity;

import com.ssafy.mimo.common.BaseDeletableEntity;
import com.ssafy.mimo.domain.hub.entity.Hub;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "HOUSE")
public class House extends BaseDeletableEntity {

    @NotNull
    private String address;

    @OneToMany(mappedBy = "house")
    private List<UserHouse> userHouse;

    @OneToMany(mappedBy = "house")
    private List<Hub> hub;
}
