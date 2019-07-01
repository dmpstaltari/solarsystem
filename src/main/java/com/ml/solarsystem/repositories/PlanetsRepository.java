package com.ml.solarsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ml.solarsystem.models.Planet;

public interface PlanetsRepository extends MongoRepository<Planet, String> {

}
