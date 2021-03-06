package com.example.fleet.models;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Trailer {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "max_weight")
    private BigDecimal maxWeight;

    @NotBlank
    private String model;

}
