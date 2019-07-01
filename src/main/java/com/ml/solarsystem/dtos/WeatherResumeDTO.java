package com.ml.solarsystem.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResumeDTO {
	
	@JsonProperty("cantidadPeriodosLluvia")
	private long rainPeriods;
	
	@JsonProperty("cantidadPeriodosOptima")
	private long optimumPeriods;
	
	@JsonProperty("cantidadPeriodosNormal")
	private long normalPeriods;
	
	@JsonProperty("cantidadPeriodosSequia")
	private long droughtPeriods;
	
	@JsonProperty("maximaIntensidadLluvia")
	private double maxIntensityRain;
	
	@JsonProperty("diasMaximaIntensidadLluvia")
	private List<Integer> maxIntensityRainDays = new ArrayList<>();
	
	@JsonProperty("climaLluviaIntensa")
	private List<WeatherDTO> intensityRainWeahters = new ArrayList<>();

}
