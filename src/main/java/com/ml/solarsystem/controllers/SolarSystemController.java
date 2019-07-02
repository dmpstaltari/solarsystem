package com.ml.solarsystem.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.solarsystem.config.SolarSystemDefaultConfig;
import com.ml.solarsystem.dtos.SolarSystemDTO;
import com.ml.solarsystem.dtos.WeatherDTO;
import com.ml.solarsystem.dtos.WeatherResumeDTO;
import com.ml.solarsystem.models.Planet;
import com.ml.solarsystem.models.SolarSystem;
import com.ml.solarsystem.models.Weather;
import com.ml.solarsystem.services.ISolarSystemService;

@RestController
@RequestMapping("/solarsystem")
@Validated
public class SolarSystemController {

	private final Logger log = LoggerFactory.getLogger(SolarSystemController.class);

	@Autowired
	private ISolarSystemService solarSystemService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<SolarSystemDTO>> getAllSolarSystems() {
		log.debug("Getting all solar systems");
		List<SolarSystem> solarSystems = solarSystemService.getAllSolarSystems();
		return ResponseEntity.ok(solarSystems.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SolarSystemDTO> getSolarSystem(@PathVariable("id") @NotBlank String solarSystemsystemId) {
		log.debug("Getting solar system whit id: {}", solarSystemsystemId);
		return ResponseEntity.ok(this.convertToDto(solarSystemService.getSolarSystem(solarSystemsystemId)));
	}

	@PostMapping("/createDefault")
	public ResponseEntity<SolarSystemDTO> createDefaultSolarSystem() throws URISyntaxException {
		log.debug("Creating the default solar system");
		SolarSystemDTO solarSystemDTO = SolarSystemDefaultConfig.getInstance().getSolarSystemDto();
		SolarSystem solarSystem = this.convertToEntity(solarSystemDTO);
		solarSystem = solarSystemService.createSolarSystem(solarSystem.getPlanets(), solarSystem.getDaysOfYear());
		return ResponseEntity.created(new URI("/solarsystem/" + solarSystem.getId())).body(this.convertToDto(solarSystem));
	}

	@PostMapping
	public ResponseEntity<SolarSystemDTO> createSolarSystem(@RequestBody @Valid SolarSystemDTO solarSystemDTO) throws URISyntaxException {
		log.debug("Creating a new solar system with planets: {}", solarSystemDTO.getPlanets());
		SolarSystem solarSystem = this.convertToEntity(solarSystemDTO);
		solarSystem = solarSystemService.createSolarSystem(solarSystem.getPlanets(), solarSystem.getDaysOfYear());
		return ResponseEntity.created(new URI("/solarsystem/" + solarSystem.getId())).body(this.convertToDto(solarSystem));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSolarSystem(@PathVariable("id") @NotBlank String solarSystemId) {
		log.debug("Deleting solar system with id: {}", solarSystemId);
		solarSystemService.deleteSolarSystem(solarSystemId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}/clima")
	public ResponseEntity<Object> getWeatherForDay(@PathVariable("id") @NotBlank String solarSystemId, @RequestParam(value = "dia", required = false)  @Min(0) Integer day) {

		if (day != null) {
			log.debug("Getting {} day weather for solar system with id: {}", day, solarSystemId);
			return ResponseEntity.ok(this.convertToDto(solarSystemService.getWeatherForDay(solarSystemId, day)));
		}

		log.debug("Getting weather for solar system with id: {}", solarSystemId);
		return ResponseEntity.ok(solarSystemService.getWeather(solarSystemId).stream()
				.map(this::convertToDto)
				.collect(Collectors.toList()));
	}

	@GetMapping("/{id}/clima/resumen")
	public ResponseEntity<WeatherResumeDTO> getWeatherResume(@PathVariable("id") @NotBlank String solarSystemId) {
		log.debug("Getting weather forecasts for solar system with id: {}", solarSystemId);
		return ResponseEntity.ok(solarSystemService.getWeatherResume(solarSystemId));
	}

	private SolarSystemDTO convertToDto(SolarSystem solarSystem) {
		return modelMapper.map(solarSystem, SolarSystemDTO.class);
	}

	private SolarSystem convertToEntity(SolarSystemDTO solarSystemDto) {
		SolarSystem solarSystem = modelMapper.map(solarSystemDto, SolarSystem.class);
		solarSystem.getPlanets().forEach(Planet::calculateOriginAngle);
		return solarSystem;
	}

	private WeatherDTO convertToDto(Weather weather) {
		return modelMapper.map(weather, WeatherDTO.class);
	}

}