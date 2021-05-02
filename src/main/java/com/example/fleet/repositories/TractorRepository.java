package com.example.fleet.repositories;

import com.example.fleet.models.Tractor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TractorRepository extends CrudRepository<Tractor, UUID> {
}
