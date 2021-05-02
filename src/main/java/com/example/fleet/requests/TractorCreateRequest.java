package com.example.fleet.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TractorCreateRequest {

    @NotBlank
    private String model;
}
