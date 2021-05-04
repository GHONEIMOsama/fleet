package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
public class TruckPatchRequest {

    private String label;

    private UUID tractorId;

    private UUID trailerId;

}
