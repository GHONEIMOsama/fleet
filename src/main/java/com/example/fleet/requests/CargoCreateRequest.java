package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class CargoCreateRequest {

    @NotNull
    @Positive
    private BigDecimal weight;

}
