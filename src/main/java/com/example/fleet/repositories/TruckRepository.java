package com.example.fleet.repositories;

import com.example.fleet.models.Truck;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TruckRepository extends CrudRepository<Truck, UUID> {
}
