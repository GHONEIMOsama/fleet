package com.example.fleet.repositories;

import com.example.fleet.models.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DriverRepository extends CrudRepository<Driver, UUID> {
}
