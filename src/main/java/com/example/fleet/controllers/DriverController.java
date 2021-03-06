package com.example.fleet.controllers;

import com.example.fleet.models.Driver;
import com.example.fleet.repositories.DriverRepository;
import com.example.fleet.requests.DriverCreateRequest;
import com.example.fleet.requests.DriverPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private static final String UNFOUND_DRIVER_MESSAGE = "Can't find driver with id: %s";

    private final DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Driver>> findAll() {
        return new ResponseEntity<>(driverRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> findOne(@PathVariable UUID id) {
        Driver driver = driverRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, id));
        });
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Driver> create(@RequestBody @Valid DriverCreateRequest driverCreateRequest) {
        Driver driver = new Driver();
        driver.setName(driverCreateRequest.getName());
        driver = driverRepository.save(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody DriverPatchRequest driverPatchRequest) {
        Driver driver = driverRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, id));
        });
        if (driverPatchRequest.getName() != null && !driverPatchRequest.getName().isBlank()) {
            driver.setName(driverPatchRequest.getName());
        }
        driverRepository.save(driver);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        driverRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
