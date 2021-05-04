package com.example.fleet.services.impl;

import com.example.fleet.exceptions.ExceedWeightException;
import com.example.fleet.models.Cargo;
import com.example.fleet.models.Driver;
import com.example.fleet.models.Expedition;
import com.example.fleet.models.Truck;
import com.example.fleet.repositories.CargoRepository;
import com.example.fleet.repositories.DriverRepository;
import com.example.fleet.repositories.ExpeditionRepository;
import com.example.fleet.repositories.TruckRepository;
import com.example.fleet.requests.ExpeditionCreateRequest;
import com.example.fleet.requests.ExpeditionPatchRequest;
import com.example.fleet.services.ExpeditionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class ExpeditionServiceImpl implements ExpeditionService {

    private final static String UNFOUND_EXPEDITION_MESSAGE = "Can't find expedition with id: %s";
    private final static String UNFOUND_DRIVER_MESSAGE = "Can't find driver with id: %s";
    private final static String UNFOUND_TRUCK_MESSAGE = "Can't find truck with id: %s";
    private final static String UNFOUND_CARGO_MESSAGE = "Can't find cargo with id: %s";
    private final static String EXCEED_WEIGHT_MESSAGE = "Can't join cargo with weight %s with a trailer with maximum weight %s";


    private final ExpeditionRepository expeditionRepository;
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;
    private final CargoRepository cargoRepository;

    public ExpeditionServiceImpl(ExpeditionRepository expeditionRepository, DriverRepository driverRepository, TruckRepository truckRepository, CargoRepository cargoRepository) {
        this.expeditionRepository = expeditionRepository;
        this.driverRepository = driverRepository;
        this.truckRepository = truckRepository;
        this.cargoRepository = cargoRepository;
    }


    @Override
    public Expedition create(ExpeditionCreateRequest expeditionCreateRequest) {
        Expedition expedition = new Expedition();
        Driver driver = driverRepository.findById(expeditionCreateRequest.getDriverId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_DRIVER_MESSAGE, expeditionCreateRequest.getDriverId()));
        });
        expedition.setDriver(driver);
        Truck truck = truckRepository.findById(expeditionCreateRequest.getTruckId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_TRUCK_MESSAGE, expeditionCreateRequest.getTruckId()));
        });
        Cargo cargo = cargoRepository.findById(expeditionCreateRequest.getCargoId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format(UNFOUND_CARGO_MESSAGE, expeditionCreateRequest.getCargoId()));
        });
        if (truck.getTrailer().getMaxWeight().compareTo(cargo.getWeight()) > -1) {
            expedition.setTruck(truck);
            expedition.setCargo(cargo);
        } else {
            throw new ExceedWeightException(String.format(EXCEED_WEIGHT_MESSAGE, cargo.getWeight(), truck.getTrailer().getMaxWeight()));
        }
        expedition.setStartTime(expeditionCreateRequest.getStartTime());
        return expeditionRepository.save(expedition);
    }

    @Override
    public void update(UUID id, ExpeditionPatchRequest expeditionPatchRequest) {
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
    }
}
