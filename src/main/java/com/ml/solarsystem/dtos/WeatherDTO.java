package com.ml.solarsystem.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
	@Min(0)
	@JsonProperty("dia")
	private int day;

	@NotBlank
	@JsonProperty("clima")
	private String condition;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("intensidad")
	private double intensity;
	
}
