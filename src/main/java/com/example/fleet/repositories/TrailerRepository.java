package com.example.fleet.repositories;

import com.example.fleet.models.Trailer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrailerRepository extends CrudRepository<Trailer, UUID> {
}
