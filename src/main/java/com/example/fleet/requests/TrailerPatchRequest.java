package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class TrailerPatchRequest {

    @Positive
    private BigDecimal maxWeight;

    private String model;
}
