package com.example.fleet.services;

import com.example.fleet.exceptions.ExceedWeightException;
import com.example.fleet.models.Expedition;
import com.example.fleet.requests.ExpeditionCreateRequest;
import com.example.fleet.requests.ExpeditionPatchRequest;

import java.util.UUID;

public interface ExpeditionService {

    Expedition create(ExpeditionCreateRequest expeditionCreateRequest);

    void update(UUID id, ExpeditionPatchRequest expeditionPatchRequest);

}
