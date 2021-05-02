package com.example.fleet.controllers;

import com.example.fleet.models.Driver;
import com.example.fleet.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
    public ResponseEntity<Driver> findOne(@PathVariable String id) {
        Driver driver = driverRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, id));
        });
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }
}
