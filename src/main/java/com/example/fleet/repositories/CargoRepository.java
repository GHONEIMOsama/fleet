package com.example.fleet.repositories;

import com.example.fleet.models.Cargo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CargoRepository extends CrudRepository<Cargo, UUID> {
}
