package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class CargoPatchRequest {

    @Positive
    private BigDecimal weight;

}
