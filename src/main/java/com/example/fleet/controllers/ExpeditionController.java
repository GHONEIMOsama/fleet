package com.example.fleet.controllers;

import com.example.fleet.exceptions.ExceedWeightException;
import com.example.fleet.models.*;
import com.example.fleet.repositories.CargoRepository;
import com.example.fleet.repositories.DriverRepository;
import com.example.fleet.repositories.ExpeditionRepository;
import com.example.fleet.repositories.TruckRepository;
import com.example.fleet.requests.ExpeditionCreateRequest;
import com.example.fleet.requests.ExpeditionPatchRequest;
import com.example.fleet.services.ExpeditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/expeditions")
public class ExpeditionController {

    private final static String UNFOUND_EXPEDITION_MESSAGE = "Can't find expedition with id: %s";

    private final ExpeditionRepository expeditionRepository;
    private final ExpeditionService expeditionService;

    public ExpeditionController(ExpeditionRepository expeditionRepository, ExpeditionService expeditionService) {
        this.expeditionRepository = expeditionRepository;
        this.expeditionService = expeditionService;
    }


    @GetMapping
    public ResponseEntity<Iterable<Expedition>> findAll() {
        return new ResponseEntity<>(expeditionRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expedition> findOne(@PathVariable UUID id) {
        Expedition expedition = expeditionRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_EXPEDITION_MESSAGE, id));
        });
        return new ResponseEntity<>(expedition, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expedition> create(@RequestBody @Valid ExpeditionCreateRequest expeditionCreateRequest) {
        return new ResponseEntity<>(expeditionService.create(expeditionCreateRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ExpeditionPatchRequest expeditionPatchRequest) {
        expeditionService.update(id, expeditionPatchRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        expeditionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
