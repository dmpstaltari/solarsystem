package com.ml.solarsystem.services;

import java.util.List;

import com.ml.solarsystem.dtos.WeatherResumeDTO;
import com.ml.solarsystem.models.Planet;
import com.ml.solarsystem.models.SolarSystem;
import com.ml.solarsystem.models.Weather;

public interface ISolarSystemService {
   
    List<SolarSystem> getAllSolarSystems();
	
    SolarSystem getSolarSystem(String solarSystemId);
	
    SolarSystem createSolarSystem(List<Planet> planets, int daysOfYear);

    void deleteSolarSystem(String solarSystemId);
    
    List<Weather> getWeather(String solarSystemId);
    
    WeatherResumeDTO getWeatherResume(String solarSystemId);

    Weather getWeatherForDay(String solarSystemId, int day);
  
}
