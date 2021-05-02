package com.example.fleet.controllers;

import com.example.fleet.models.Driver;
import com.example.fleet.models.Tractor;
import com.example.fleet.repositories.TractorRepository;
import com.example.fleet.requests.DriverCreateRequest;
import com.example.fleet.requests.DriverPatchRequest;
import com.example.fleet.requests.TractorCreateRequest;
import com.example.fleet.requests.TractorPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/tractors")
public class TractorController {

    private static final String UNFOUND_TRACTOR_MESSAGE = "Can't find tractor with id: %s";

    private final TractorRepository tractorRepository;

    public TractorController(TractorRepository tractorRepository) {
        this.tractorRepository = tractorRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Tractor>> findAll() {
        return new ResponseEntity<>(tractorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tractor> findOne(@PathVariable UUID id) {
        Tractor tractor = tractorRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRACTOR_MESSAGE, id));
        });
        return new ResponseEntity<>(tractor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tractor> create(@RequestBody @Valid TractorCreateRequest tractorCreateRequest) {
        Tractor tractor = new Tractor();
        tractor.setModel(tractorCreateRequest.getModel());
        tractor = tractorRepository.save(tractor);
        return new ResponseEntity<>(tractor, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody TractorPatchRequest tractorPatchRequest) {
        Tractor tractor = tractorRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRACTOR_MESSAGE, id));
        });
        if (tractorPatchRequest.getModel() != null) {
            tractor.setModel(tractorPatchRequest.getModel());
        }
        tractorRepository.save(tractor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tractorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
