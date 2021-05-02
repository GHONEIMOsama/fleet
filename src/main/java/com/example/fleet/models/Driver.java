package com.example.fleet.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Driver {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;
}
