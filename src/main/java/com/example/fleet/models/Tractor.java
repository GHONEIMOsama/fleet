package com.example.fleet.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Tractor {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String model;

}
