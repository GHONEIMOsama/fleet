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

    @NotNull
    private UUID driverId;

    @NotNull
    private UUID truckId;

    @NotNull
    private UUID cargoId;

}
