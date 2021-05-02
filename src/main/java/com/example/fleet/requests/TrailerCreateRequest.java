package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class TrailerCreateRequest {

    @NotNull
    @Positive
    private BigDecimal maxWeight;

    @NotBlank
    private String model;

}
