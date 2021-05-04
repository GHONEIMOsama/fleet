package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class TruckCreateRequest {

    @NotBlank
    private String label;

    @NotNull
    private UUID tractorId;

    @NotNull
    private UUID trailerId;
}
