package com.ml.solarsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ml.solarsystem.models.SolarSystem;

@Repository
public interface SolarSystemRepository extends MongoRepository<SolarSystem, String> {
}
