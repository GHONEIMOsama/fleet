package com.example.fleet.controllers;

import com.example.fleet.models.Tractor;
import com.example.fleet.models.Trailer;
import com.example.fleet.repositories.TrailerRepository;
import com.example.fleet.requests.TractorCreateRequest;
import com.example.fleet.requests.TractorPatchRequest;
import com.example.fleet.requests.TrailerCreateRequest;
import com.example.fleet.requests.TrailerPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/trailers")
public class TrailerController {

    private static final String UNFOUND_TRAILER_MESSAGE = "Can't find trailer with id: %s";

    private final TrailerRepository trailerRepository;

    public TrailerController(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Trailer>> findAll() {
        return new ResponseEntity<>(trailerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trailer> findOne(@PathVariable UUID id) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRAILER_MESSAGE, id));
        });
        return new ResponseEntity<>(trailer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trailer> create(@RequestBody @Valid TrailerCreateRequest trailerCreateRequest) {
        Trailer trailer = new Trailer();
        trailer.setModel(trailerCreateRequest.getModel());
        trailer.setMaxWeight(trailerCreateRequest.getMaxWeight());
        trailer = trailerRepository.save(trailer);
        return new ResponseEntity<>(trailer, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody TrailerPatchRequest trailerPatchRequest) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRAILER_MESSAGE, id));
        });
        if (trailerPatchRequest.getModel() != null) {
            trailer.setModel(trailerPatchRequest.getModel());
        }
        if (trailerPatchRequest.getMaxWeight() != null) {
            trailer.setMaxWeight(trailerPatchRequest.getMaxWeight());
        }
        trailerRepository.save(trailer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        trailerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
