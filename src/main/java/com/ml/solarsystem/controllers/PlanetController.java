package com.ml.solarsystem.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ml.solarsystem.exceptions.ResourceNotFoundException;
import com.ml.solarsystem.models.Planet;
import com.ml.solarsystem.repositories.PlanetsRepository;

@RestController
@RequestMapping("/planets")
public class PlanetController {

	@Autowired
	private PlanetsRepository repository;

	@GetMapping
	public List<Planet> getAll() {
	  return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Planet add(@Valid @RequestBody Planet planet) {
	  return repository.save(planet);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void delete(@PathVariable String id) {  
	    Planet planet = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.delete(planet);
	}
	
}
