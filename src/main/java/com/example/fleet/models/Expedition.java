package com.example.fleet.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Expedition {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @OneToOne(optional = false)
    private Driver driver;

    @OneToOne
    private Truck truck;

    @OneToOne
    private Cargo cargo;
}
