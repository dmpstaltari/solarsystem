package com.ml.solarsystem.models;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Weather {

	public enum Condition {
		RAIN("lluvia"),
		INTENSE_RAIN("lluvia-intensa"),
		OPTIMUM("optimo"),
		NORMAL("normal"),
		DROUGHT("sequia");

		private final String name;

		Condition(String name) {
			this.name = name;
		}

		@JsonValue
		@Override
		public String toString() {
			return name;
		}
	}

	@PositiveOrZero
	@JsonProperty("dia")
	private int day;
	
	@NotNull
	@JsonProperty("clima")
	private Condition condition;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private double intensity;
	
	public Weather setDay(int day) {
		this.day = day;
		return this;
	}

	public Weather setCondition(Condition condition) {
		this.condition = condition;
		return this;
	}

	public Weather setIntensity(double intensity) {
		this.intensity = intensity;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Weather weather = (Weather) o;
		return day == weather.day;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day);
	}

}
