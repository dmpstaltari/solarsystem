package com.ml.solarsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ml.solarsystem.exceptions.ResourceNotFoundException;
import com.ml.solarsystem.models.SolarSystem;
import com.ml.solarsystem.repositories.SolarSystemRepository;

@Service
public class SolarSystemAsyncService {
	@Autowired
	SolarSystemRepository solarSystemRepository;

	@Value("${solarsystem.weather-forecast.years}")
	private int years;

	@Async
	public void createSolarSystem(String solarSystemId) {
		SolarSystem solarSystem = solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new);
		
		int weatherForecastDays = solarSystem.getDaysOfYear() * years;
		
		for (int i = 0; i <= weatherForecastDays; i++) {
			solarSystem.getWeatherForDay(i);
		}
		solarSystem.setLastProcessedDay(weatherForecastDays);
		solarSystemRepository.save(solarSystem);
	}

	@Scheduled(cron = "0 0 0 * * *",zone = "America/Buenos_Aires")
	public void weatherForecastForNextDay() {
		solarSystemRepository.findAll().forEach(solarSystem -> {
			int day = solarSystem.getLastProcessedDay();
			day++;
			solarSystem.getWeatherForDay(day);
			solarSystem.setLastProcessedDay(day);
			solarSystemRepository.save(solarSystem);
		});
	}
}
