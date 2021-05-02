package com.example.fleet.models;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Cargo {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private BigDecimal weight;

}
