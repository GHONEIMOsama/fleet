package com.example.fleet.requests;

import com.example.fleet.models.Cargo;
import com.example.fleet.models.Driver;
import com.example.fleet.models.Truck;
import lombok.Getter;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class ExpeditionCreateRequest {

    @NotNull
    private ZonedDateTime startTime;

    @NotBlank
    private UUID driverId;

    @NotBlank
    private UUID truckId;

    @NotBlank
    private UUID cargoId;

}
