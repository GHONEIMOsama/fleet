package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
public class TruckCreateRequest {

    @NotBlank
    private String label;

    @NotBlank
    private UUID tractorId;

    @NotBlank
    private UUID trailerId;
}
