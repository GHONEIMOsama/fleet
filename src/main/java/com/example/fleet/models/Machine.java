package com.example.fleet.models;

import javax.validation.constraints.NotBlank;

public abstract class Machine {

    @NotBlank
    private String model;
}
