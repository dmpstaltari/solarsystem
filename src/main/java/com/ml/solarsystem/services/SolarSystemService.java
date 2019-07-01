package com.ml.solarsystem.services;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ml.solarsystem.dtos.WeatherDTO;
import com.ml.solarsystem.dtos.WeatherResumeDTO;
import com.ml.solarsystem.exceptions.ResourceNotFoundException;
import com.ml.solarsystem.models.Planet;
import com.ml.solarsystem.models.SolarSystem;
import com.ml.solarsystem.models.Weather;
import com.ml.solarsystem.repositories.SolarSystemRepository;

@Service
public class SolarSystemService implements ISolarSystemService {

	@Autowired
	private SolarSystemRepository solarSystemRepository;

	@Autowired
	private SolarSystemAsyncService solarSystemAsyncService;
	
	@Value("${solarsystem.weather-forecast.years}")
	private int years;

	@Override
	public List<SolarSystem> getAllSolarSystems() {
		return solarSystemRepository.findAll();   
	}

	@Override
	public SolarSystem getSolarSystem(String solarSystemId){
		return solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public SolarSystem createSolarSystem(List<Planet> planets, int daysOfYear) {
		SolarSystem solarSystem = new SolarSystem(planets);
		solarSystem.setDaysOfYear(daysOfYear);
		solarSystem = solarSystemRepository.save(solarSystem);
		solarSystemAsyncService.createSolarSystem(solarSystem.getId());
		return solarSystem;
	}

	@Override
	public void deleteSolarSystem(String solarSystemId) {
		SolarSystem solarSystem = solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new);
		solarSystemRepository.delete(solarSystem);
	}

	@Override
	public List<Weather> getWeather(String solarSystemId){
		return solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new).getWeather().values().stream().collect(Collectors.toList());
	}

	@Override
	public Weather getWeatherForDay(String solarSystemId, int day) {
		SolarSystem solarSystem = solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new);

		if (solarSystem.getWeather().containsKey(day)) {
			return solarSystem.getWeather().get(day);
		} else {
			Weather weather = solarSystem.getWeatherForDay(day);
			solarSystemRepository.save(solarSystem);
			return weather;
		}
	}

	@Override
	public WeatherResumeDTO getWeatherResume(String solarSystemId) {
		SolarSystem solarSystem = solarSystemRepository.findById(solarSystemId).orElseThrow(ResourceNotFoundException::new);

		int daysOfResume = solarSystem.getDaysOfYear() * years; 
		int endDay = solarSystem.getLastProcessedDay();
		int startDay = endDay - daysOfResume;
		
		SolarSystemService.calculateWeatherForPeriod(solarSystem, startDay, endDay);

		final Map<Weather.Condition, Long> counters = solarSystem.getWeather().values()
				.stream()
				.collect(Collectors.groupingBy(Weather::getCondition, Collectors.counting()));

		WeatherResumeDTO weatherResume = new WeatherResumeDTO();
		
		weatherResume.setRainPeriods(Optional.ofNullable(counters.get(Weather.Condition.DROUGHT)).orElse(0l));
		weatherResume.setOptimumPeriods(Optional.ofNullable(counters.get(Weather.Condition.OPTIMUM)).orElse(0l));
		weatherResume.setNormalPeriods(Optional.ofNullable(counters.get(Weather.Condition.NORMAL)).orElse(0l));
		weatherResume.setDroughtPeriods(Optional.ofNullable(counters.get(Weather.Condition.DROUGHT)).orElse(0l));
		
		Predicate<Weather> weatherIntenseRain = weather -> Weather.Condition.INTENSE_RAIN.equals(weather.getCondition()); 

		List<Weather> intenseRainWeather = solarSystem.getWeather().values().stream().filter(weatherIntenseRain).collect(Collectors.toList());

		intenseRainWeather.forEach(weather -> weatherResume.getIntensityRainWeahters().add( new WeatherDTO(weather.getDay(),weather.getCondition().toString(), weather.getIntensity())));
		
		double maxIntensity = intenseRainWeather.stream().max(Comparator.comparing(Weather::getIntensity)).orElseThrow(NoSuchElementException::new).getIntensity();
		
		weatherResume.setMaxIntensityRain(maxIntensity);
		
		List<Weather> maxIntenseRainWheater = intenseRainWeather.stream().filter(weather -> weather.getIntensity() + 0.00001 >= maxIntensity).collect(Collectors.toList());
		
		maxIntenseRainWheater.forEach(weather -> weatherResume.getMaxIntensityRainDays().add(weather.getDay()));

		return weatherResume;
	}

	public static void calculateWeatherForPeriod(SolarSystem solarSystem, int startDay, int endDay) {
		for (int i = startDay; i <= endDay; i++) {
			solarSystem.getWeatherForDay(i);
		}
	}


}
