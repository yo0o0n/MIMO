package com.ssafy.mimo.domain.hub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HUB")
public class Hub {
    @Column
    private Integer id;
//    @ManyToOne
//    private House house;
    @Column(columnDefinition = "boolean default false")
    private Boolean isRegistered;
    @Column
    private String serialNumber;
    @Column
    private LocalDateTime registeredDttm;
    @Column
    private LocalDateTime unregisteredDttm;
    @Column
    private String nickname;
}
