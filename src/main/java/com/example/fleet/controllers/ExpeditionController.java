package com.example.fleet.controllers;

import com.example.fleet.models.*;
import com.example.fleet.repositories.CargoRepository;
import com.example.fleet.repositories.DriverRepository;
import com.example.fleet.repositories.ExpeditionRepository;
import com.example.fleet.repositories.TruckRepository;
import com.example.fleet.requests.ExpeditionCreateRequest;
import com.example.fleet.requests.ExpeditionPatchRequest;
import com.example.fleet.requests.TruckCreateRequest;
import com.example.fleet.requests.TruckPatchRequest;
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
    private final static String UNFOUND_DRIVER_MESSAGE = "Can't find driver with id: %s";
    private final static String UNFOUND_TRUCK_MESSAGE = "Can't find truck with id: %s";
    private final static String UNFOUND_CARGO_MESSAGE = "Can't find cargo with id: %s";

    private final ExpeditionRepository expeditionRepository;
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;
    private final CargoRepository cargoRepository;

    public ExpeditionController(ExpeditionRepository expeditionRepository, DriverRepository driverRepository, TruckRepository truckRepository, CargoRepository cargoRepository) {
        this.expeditionRepository = expeditionRepository;
        this.driverRepository = driverRepository;
        this.truckRepository = truckRepository;
        this.cargoRepository = cargoRepository;
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
        Expedition expedition = new Expedition();
        Driver driver = driverRepository.findById(expeditionCreateRequest.getDriverId()).orElseThrow(() -> {
           throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, expeditionCreateRequest.getDriverId()));
        });
        expedition.setDriver(driver);
        Truck truck = truckRepository.findById(expeditionCreateRequest.getTruckId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRUCK_MESSAGE, expeditionCreateRequest.getTruckId()));
        });
        expedition.setTruck(truck);
        Cargo cargo = cargoRepository.findById(expeditionCreateRequest.getCargoId()).orElseThrow(() -> {
           throw new EntityNotFoundException(String.format(UNFOUND_CARGO_MESSAGE, expeditionCreateRequest.getCargoId()));
        });
        expedition.setCargo(cargo);
        expeditionRepository.save(expedition);
        return new ResponseEntity<>(expedition, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ExpeditionPatchRequest expeditionPatchRequest) {
        Expedition expedition = expeditionRepository.findById(id).orElseThrow(() -> {
           throw new EntityNotFoundException(String.format(UNFOUND_EXPEDITION_MESSAGE, id));
        });
        if (expeditionPatchRequest.getCargoId() != null) {
            Cargo cargo = cargoRepository.findById(expeditionPatchRequest.getCargoId()).orElseThrow(() -> {
               throw new EntityNotFoundException(String.format(UNFOUND_CARGO_MESSAGE, expeditionPatchRequest.getCargoId()));
            });
            expedition.setCargo(cargo);
        }
        if (expeditionPatchRequest.getTruckId() != null) {
            Truck truck = truckRepository.findById(expeditionPatchRequest.getTruckId()).orElseThrow(() -> {
                throw new EntityNotFoundException(String.format(UNFOUND_TRUCK_MESSAGE, expeditionPatchRequest.getTruckId()));
            });
            expedition.setTruck(truck);
        }
        if (expeditionPatchRequest.getDriverId() != null) {
            Driver driver = driverRepository.findById(expeditionPatchRequest.getDriverId()).orElseThrow(() -> {
               throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, expeditionPatchRequest.getDriverId()));
            });
            expedition.setDriver(driver);
        }
        if (expeditionPatchRequest.getStartTime() != null) {
            expedition.setStartTime(expeditionPatchRequest.getStartTime());
        }
        expeditionRepository.save(expedition);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        expeditionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
