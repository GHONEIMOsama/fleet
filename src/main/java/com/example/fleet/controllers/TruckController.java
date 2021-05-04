package com.example.fleet.controllers;

import com.example.fleet.models.Tractor;
import com.example.fleet.models.Trailer;
import com.example.fleet.models.Truck;
import com.example.fleet.repositories.TractorRepository;
import com.example.fleet.repositories.TrailerRepository;
import com.example.fleet.repositories.TruckRepository;
import com.example.fleet.requests.TruckCreateRequest;
import com.example.fleet.requests.TruckPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final static String UNFOUND_TRUCK_MESSAGE = "Can't find truck with id: %s";
    private static final String UNFOUND_TRACTOR_MESSAGE = "Can't find tractor with id: %s";
    private static final String UNFOUND_TRAILER_MESSAGE = "Can't find trailer with id: %s";

    private final TruckRepository truckRepository;
    private final TractorRepository tractorRepository;
    private final TrailerRepository trailerRepository;

    public TruckController(TruckRepository truckRepository, TractorRepository tractorRepository, TrailerRepository trailerRepository) {
        this.truckRepository = truckRepository;
        this.tractorRepository = tractorRepository;
        this.trailerRepository = trailerRepository;
    }


    @GetMapping
    public ResponseEntity<Iterable<Truck>> findAll() {
        return new ResponseEntity<>(truckRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Truck> findOne(@PathVariable UUID id) {
        Truck truck = truckRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRUCK_MESSAGE, id));
        });
        return new ResponseEntity<>(truck, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Truck> create(@RequestBody @Valid TruckCreateRequest truckCreateRequest) {
        Truck truck = new Truck();
        truck.setLabel(truckCreateRequest.getLabel());
        Tractor tractor = tractorRepository.findById(truckCreateRequest.getTractorId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRACTOR_MESSAGE, truckCreateRequest.getTractorId()));
        });
        truck.setTractor(tractor);
        Trailer trailer = trailerRepository.findById(truckCreateRequest.getTrailerId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRAILER_MESSAGE, truckCreateRequest.getTrailerId()));
        });
        truck.setTrailer(trailer);
        truck = truckRepository.save(truck);
        return new ResponseEntity<>(truck, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody TruckPatchRequest truckPatchRequest) {
        Truck truck = truckRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRUCK_MESSAGE, id));
        });
        if (truckPatchRequest.getLabel() != null) {
            truck.setLabel(truckPatchRequest.getLabel());
        }
        if (truckPatchRequest.getTractorId() != null) {
            Tractor tractor = tractorRepository.findById(truckPatchRequest.getTractorId()).orElseThrow(() -> {
                throw new EntityNotFoundException(String.format(UNFOUND_TRACTOR_MESSAGE, truckPatchRequest.getTractorId()));
            });
            truck.setTractor(tractor);
        }
        if (truckPatchRequest.getTrailerId() != null) {
            Trailer trailer = trailerRepository.findById(truckPatchRequest.getTrailerId()).orElseThrow(() -> {
                throw new EntityNotFoundException(String.format(UNFOUND_TRAILER_MESSAGE, truckPatchRequest.getTrailerId()));
            });
            truck.setTrailer(trailer);
        }
        truckRepository.save(truck);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        truckRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
