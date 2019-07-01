package com.ml.solarsystem.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.ml.solarsystem.models.Planet.Rotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanetDTO {
	
	@NotBlank
	private String name;

	@NotNull
	@PositiveOrZero
	private Double distance;

	@NotNull
	@PositiveOrZero
	private Double speed;

	@NotNull
	private Double originX;
	
	@NotNull
	private Double originY;

	@NotNull
	private Rotation rotation;

}
