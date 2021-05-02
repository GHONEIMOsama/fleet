package com.example.fleet.controllers;

import com.example.fleet.models.Cargo;
import com.example.fleet.models.Trailer;
import com.example.fleet.repositories.CargoRepository;
import com.example.fleet.requests.CargoCreateRequest;
import com.example.fleet.requests.CargoPatchRequest;
import com.example.fleet.requests.TrailerCreateRequest;
import com.example.fleet.requests.TrailerPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/cargoes")
public class CargoController {

    private static final String UNFOUND_CARGO_MESSAGE = "Can't find cargo with id: %s";

    private final CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Cargo>> findAll() {
        return new ResponseEntity<>(cargoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> findOne(@PathVariable UUID id) {
        Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_CARGO_MESSAGE, id));
        });
        return new ResponseEntity<>(cargo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cargo> create(@RequestBody @Valid CargoCreateRequest cargoCreateRequest) {
        Cargo cargo = new Cargo();
        cargo.setWeight(cargoCreateRequest.getWeight());
        cargo = cargoRepository.save(cargo);
        return new ResponseEntity<>(cargo, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CargoPatchRequest cargoPatchRequest) {
        Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_CARGO_MESSAGE, id));
        });
        if (cargoPatchRequest.getWeight() != null) {
            cargo.setWeight(cargoPatchRequest.getWeight());
        }
        cargoRepository.save(cargo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cargoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
