package com.example.fleet.repositories;

import com.example.fleet.models.Expedition;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExpeditionRepository extends CrudRepository<Expedition, UUID> {
}
