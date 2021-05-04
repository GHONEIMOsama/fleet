package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class ExpeditionPatchRequest {

    private ZonedDateTime startTime;

    private UUID driverId;

    private UUID truckId;

    private UUID cargoId;

}
