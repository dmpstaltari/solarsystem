package com.ml.solarsystem.dtos;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolarSystemDTO {
	
	private String id;
	
	@Min(1)
	private int daysOfYear;
	
	@Valid
	@Size(min=3, max=3)
	private List<PlanetDTO> planets;
}
